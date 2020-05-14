package xyz.threewater.debug;

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.action.BreakPointCenter;
import xyz.threewater.action.DebugCenter;
import xyz.threewater.action.VMCenter;

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
    private final VMCenter vmCenter;
    private final BreakPointHolder breakPointHolder;

    public JvmEventHandler(BreakPointCenter breakPointCenter, DebugCenter debugCenter, VMCenter vmCenter, BreakPointHolder breakPointHolder) {
        this.breakPointCenter = breakPointCenter;
        this.debugCenter = debugCenter;
        this.vmCenter = vmCenter;
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
        //当有类准备好时，应该停止运行，让我们有足够的时间来打断点
        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
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
            eventSet.resume();
        } else if (event instanceof ClassPrepareEvent) {
            //当有类装载时，从等待队列中获取等待的断点加入
            ClassPrepareEvent classPrepareEvent = (ClassPrepareEvent) event;
            String mainClassName = classPrepareEvent.referenceType().name();
            logger.debug("{} class prepared",mainClassName);
            List<BreakPointBean> waitingBreakPoints = breakPointHolder.getWaitingBreakPoints(mainClassName);
            //当类准备好时，重新设置断点
            waitingBreakPoints.forEach(this::addBreakPoint);
            //断点设置好了就继续执行
            eventSet.resume();
        } else if (event instanceof BreakpointEvent) {
            BreakpointEvent breakpointEvent=(BreakpointEvent)event;
            int lineNumber = breakpointEvent.location().lineNumber();
            String name = breakpointEvent.location().declaringType().name();
            logger.debug("breakPoint stopped:{},{}",lineNumber,name);
        } else if (event instanceof StepEvent){
            //下一步事件触发
            StepEvent stepEvent=(StepEvent)event;
            String fullClassName = stepEvent.location().declaringType().name();
            int line=stepEvent.location().lineNumber();
            logger.debug("StepEvent Triggered:{},{}",fullClassName,line);
            //删除下一步请求
            vm.eventRequestManager().deleteEventRequest(event.request());
        } else if(event instanceof VMDisconnectEvent){
            vmExit = true;
            debugCenter.debugFinished();
            vmCenter.VmExited();
            logger.debug("vm Exited");
            vm.eventRequestManager().deleteAllBreakpoints();
            eventSet.resume();
        }
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

    public void stepOver(){
        //执行下一步
        List<ThreadReference> threadReferences = vm.allThreads();
        ThreadReference mainThead=null;
        for(ThreadReference threadReference:threadReferences){
            if(threadReference.name().equals("main")){
                mainThead=threadReference;
            }
        }
        if(mainThead==null){
            logger.warn("mainThread is Null!");
            return;
        }
        logger.debug("stepOver Request send Thread :{},",mainThead);
        StepRequest request = vm.eventRequestManager().createStepRequest(mainThead, StepRequest.STEP_LINE, StepRequest.STEP_OVER);
        //这个表示多少步之后停下来
        request.addCountFilter(1);  // next step only
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
        //发送请求之后，再恢复vm执行
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vm.resume();
    }
}
