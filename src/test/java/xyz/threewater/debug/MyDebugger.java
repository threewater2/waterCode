package xyz.threewater.debug;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.ListeningConnector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MyDebugger {
    static VirtualMachine vm;
    static Process process;
    static EventRequestManager eventRequestManager;
    static EventQueue eventQueue;
    static EventSet eventSet;
    static boolean vmExit = false;

    public static void main(String[] args) throws Exception{
        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        List<ListeningConnector> listeningConnectors = virtualMachineManager.listeningConnectors();
        ListeningConnector listeningConnector = listeningConnectors.get(0);
        Map<String, Connector.Argument> argumentMap = listeningConnector.defaultArguments();
        argumentMap.get("timeout").setValue("10000");
        argumentMap.get("port").setValue("8456");
        argumentMap.get("localAddress").setValue("localhost");
        //当连接器连上以后，会返回虚拟机对象
        vm = listeningConnector.accept(argumentMap);


        // Register ClassPrepareRequest
        eventRequestManager = vm.eventRequestManager();
        //当类装载完毕时，就会触发这个请求
        ClassPrepareRequest classPrepareRequest
                = eventRequestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter("xyz.threewater.debug.DebugDemo");
        classPrepareRequest.addCountFilter(1);
        //停止
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        //启用这个请求，当虚拟机到达这个请求时，就会处理
        classPrepareRequest.enable();
        // Enter event loop
        eventLoop();

    }

    private static void eventLoop() throws Exception {
        eventQueue = vm.eventQueue();
        while (true) {
            if (vmExit == true) {
                break;
            }
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
     * @param event
     * @throws Exception
     */
    private static void execute(Event event) throws Exception {
        if (event instanceof VMStartEvent) {
            System.out.println("VM started");
            eventSet.resume();
        } else if (event instanceof ClassPrepareEvent) {
            ClassPrepareEvent classPrepareEvent = (ClassPrepareEvent) event;
            String mainClassName = classPrepareEvent.referenceType().name();
            if (mainClassName.equals("xyz.threewater.debug.DebugDemo")) {
                System.out.println("Class " + mainClassName
                        + " is already prepared");
            }
            if (true) {
                // Get location
                ReferenceType referenceType = classPrepareEvent.referenceType();
                //获取第14行的位置对象
                List locations = referenceType.locationsOfLine(16);
                Location location = (Location) locations.get(0);

                //当类装载完毕时
                BreakpointRequest breakpointRequest = eventRequestManager
                        .createBreakpointRequest(location);
                breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                //启用这个断点
                breakpointRequest.enable();
            }
            eventSet.resume();
        } else if (event instanceof BreakpointEvent) {
            System.out.println("Reach line 14 of xyz,threewater.debug.DebugDemo");
            BreakpointEvent breakpointEvent = (BreakpointEvent) event;
            Location location = breakpointEvent.location();
            System.out.println("断点停止的位置："+location.lineNumber());
//            System.out.println("断点停止的源码路径："+location);
            //获取线程
            ThreadReference threadReference = breakpointEvent.thread();
            //获取栈帧
            StackFrame stackFrame = threadReference.frame(0);
            //获取静态成员变量
//            Value a = location.declaringType().getValue(location.declaringType().fieldByName("a"));
            //获取成员变量
//            Value b = location.declaringType().getValue(location.declaringType().fieldByName("b"));
            //静态整形变量
//            Value c = location.declaringType().getValue(location.declaringType().fieldByName("c"));
            //整形变量
//            Value d = location.declaringType().getValue(location.declaringType().fieldByName("d"));
//            System.out.println("静态成员变量是："+((StringReference)a).value()+"成员变量："+((StringReference)b).value());
//            System.out.println("静态整形变量："+((IntegerValue)c).value()+"整形变量："+((IntegerValue)d).value());
            //没进入一个方法就是一个栈帧，当前应该是main方法，通过栈帧来获取局部变量
            LocalVariable localVariable = stackFrame
                    .visibleVariableByName("fieldA");
            Value value = stackFrame.getValue(localVariable);
            String str = ((StringReference) value).value();
            LocalVariable fieldB = stackFrame.visibleVariableByName("fieldB");
            Value value1 = stackFrame.getValue(fieldB);
            int fieldBa=((IntegerValue)value1).value();
            System.out.println("The local variable fieldA is " + str
                    + " of " + value.type().name());
            System.out.println("The local variable fieldB is " + fieldBa
                    + " of " + fieldB.type().name());
            //执行下一步
            StepRequest request = eventRequestManager.createStepRequest(threadReference,
                    StepRequest.STEP_LINE,
                    StepRequest.STEP_OVER);
            request.addCountFilter(1);  // next step only
            request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            request.enable();
            eventSet.resume();
        } else if(event instanceof StepEvent){
            //第16步已经运行完，获取debugDemo试试
            StackFrame frame = ((StepEvent) event).thread().frame(0);
            LocalVariable debugDemo = frame.visibleVariableByName("debugDemo");
            Value value = frame.getValue(debugDemo);
            ObjectReference objectReference=(ObjectReference) value;
            System.out.println("debugDemo的类名是："+objectReference.type().name());
            Value b = objectReference.getValue(objectReference.referenceType().fieldByName("b"));
            StringReference stringReference=(StringReference)b;
            System.out.println("b成员变量的值是："+stringReference.value());
            StepEvent stepEvent=(StepEvent) event;
            Location location = stepEvent.location();

            System.out.println("下一步之后的行数："+location.lineNumber());
            eventSet.resume();
        } else if (event instanceof VMDisconnectEvent) {
            vmExit = true;
        }
    }
}
