package xyz.threewater.debug;

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.action.BreakPointCenter;
import xyz.threewater.action.DebugCenter;

import java.util.List;

/**
 * 用来发送和接收jvm事件
 */
@Component
public class JvmEventHandler {
    private VirtualMachine vm;
    private final Logger logger= LoggerFactory.getLogger(JvmEventHandler.class);
    private boolean vmExit=true;
    private EventSet eventSet;
    private final BreakPointCenter breakPointCenter;
    private final DebugCenter debugCenter;
    private final BreakPointHolder breakPointHolder;

    public JvmEventHandler(BreakPointCenter breakPointCenter, DebugCenter debugCenter, BreakPointHolder breakPointHolder) {
        this.breakPointCenter = breakPointCenter;
        this.debugCenter = debugCenter;
        this.breakPointHolder = breakPointHolder;
    }

    /**
     * 但这个方法被调用时，表示虚拟机已经准备就绪
     */
    public void jvmIsReady(VirtualMachine virtualMachine) {
        this.vm = virtualMachine;
        vmExit=false;
        breakPointCenter.addBreakPointRemoveListener(this::removeBreakPoint);
        breakPointCenter.addBreakPointAddListener(this::addBreakPoint);
    }

    /**
     * 阻塞方法，监听jvm发过来的事件
     */
    public void listeningEvent() throws Exception {
        EventQueue eventQueue = vm.eventQueue();
        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.enable();
        while (!vmExit) {
            //从事件队列中取出一个事件集，事件集是事件发生的最小单位
            eventSet = eventQueue.remove();
            EventIterator eventIterator = eventSet.eventIterator();
            while (eventIterator.hasNext()) {
                Event event = eventIterator.next();
                execute(event);
            }
        }
    }

    /**
     * 处理每个虚拟机发生的事件
     */
    private void execute(Event event) {
        if (event instanceof VMStartEvent) {
            logger.debug("VMStarted!");
        } else if (event instanceof ClassPrepareEvent) {
            //当有类装载时，从等待队列中获取等待的断点加入
            ClassPrepareEvent classPrepareEvent = (ClassPrepareEvent) event;
            String mainClassName = classPrepareEvent.referenceType().name();
            logger.debug("{} class prepared",mainClassName);
            List<BreakPointBean> waitingBreakPoints = breakPointHolder.getWaitingBreakPoints(mainClassName);
            //当类准备好时，重新设置断点
            waitingBreakPoints.forEach(this::addBreakPoint);
        } else if (event instanceof BreakpointEvent) {
            BreakpointEvent breakpointEvent=(BreakpointEvent)event;
            int lineNumber = breakpointEvent.location().lineNumber();
            String name = breakpointEvent.location().declaringType().name();
            logger.debug("breakPoint stopped:{},{}",lineNumber,name);
        } else if(event instanceof VMDisconnectEvent){
            vmExit = true;
            debugCenter.debugFinished();
            logger.debug("vm Exited");
        }
        eventSet.resume();
    }

    /**
     * 删除虚拟机中的断点
     */
    public void removeBreakPoint(BreakPointBean breakPointBean) {
        List<BreakpointRequest> breakpointRequests = vm.eventRequestManager().breakpointRequests();
        breakpointRequests.forEach(breakpointRequest -> {
            Location location = breakpointRequest.location();
            if(location.declaringType().name().equals(breakPointBean.getFullClassName())
                    &&location.lineNumber()==breakPointBean.getLine()){
                breakpointRequest.disable();
            }
        });
    }

    /**
     * 自动调用
     * 添加断点，如果class没有准备好，则在队列继续等待
     */
    public void addBreakPoint(BreakPointBean breakPointBean) {
        logger.debug("start to add breakPoint to jvm {}",breakPointBean);
        if(vmExit){
            logger.debug("start to add breakPoint to jvm vmExit:true");
            return;
        }
        List<ReferenceType> referenceTypes = vm.classesByName(breakPointBean.getFullClassName());
        if(referenceTypes.size()==0) {
            logger.debug("start to add breakPoint to jvm not foundClassName:{}",breakPointBean.getFullClassName());
            return;
        }
        ReferenceType referenceType = referenceTypes.get(0);
        List<Location> locations;
        try {
            locations = referenceType.locationsOfLine(breakPointBean.getLine());
        } catch (AbsentInformationException e) {
            throw new RuntimeException(e);
        }
        Location location = locations.get(0);
        BreakpointRequest breakpointRequest = vm.eventRequestManager().createBreakpointRequest(location);
        breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        breakpointRequest.enable();
        breakPointBean.setUsed(true);
        logger.debug("breakPoint added to jvm:{}",breakPointBean);
    }
}
