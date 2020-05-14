package xyz.threewater.debug;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import javafx.concurrent.Task;
import org.springframework.stereotype.Component;
import xyz.threewater.action.DebugCenter;
import xyz.threewater.action.RunAndBuildAction;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.MainClassList;
import xyz.threewater.utils.ThreadHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JavaProjectDebugger {
    private final JavaFxComponent javaFxComponent;
    private final RunAndBuildAction runAndBuildAction;
    private final MainClassList mainClassList;
    private final JvmEventHandler jvmEventHandler;
    private Thread debuggerThread;
    private final DebugCenter debugCenter;
    private VirtualMachine virtualMachine;

    public JavaProjectDebugger(JavaFxComponent javaFxComponent,
                               RunAndBuildAction runAndBuildAction,
                               MainClassList mainClassList, JvmEventHandler jvmEventHandler, DebugCenter debugCenter) {
        this.javaFxComponent = javaFxComponent;
        this.runAndBuildAction = runAndBuildAction;
        this.mainClassList = mainClassList;
        this.jvmEventHandler = jvmEventHandler;
        this.debugCenter = debugCenter;
    }



    /**
     * 开启调试器，监听虚拟机的启动
     * @param address ip地址
     * @param port 端口号
     * @param timeout 等待时间
     * @return jvm 虚拟机
     */
    private VirtualMachine listeningJvm(String address, String port, String timeout) throws IOException, IllegalConnectorArgumentsException {
        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        List<ListeningConnector> listeningConnectors =
                virtualMachineManager.listeningConnectors();
        ListeningConnector listeningConnector = listeningConnectors.get(0);
        Map<String, Connector.Argument> argumentMap = listeningConnector.defaultArguments();
        argumentMap.get("timeout").setValue(timeout);
        argumentMap.get("port").setValue(port);
        argumentMap.get("localAddress").setValue(address);
        return listeningConnector.accept(argumentMap);
    }


    public void startDebug() {
        debugCenter.debugStarted();
        Optional<String> runnableClass = mainClassList.getCurrentRunnableClass();
        //由于方法会阻塞，创建新线程，启动调试器
        if(!runnableClass.isPresent()){
            return;
        }
        Task<Void> task=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                virtualMachine = listeningJvm("127.0.0.1", "8456", "10000");
                jvmEventHandler.jvmIsReady(virtualMachine);
                //阻塞，开始监听jvm发过来的事件
                jvmEventHandler.listeningEvent();
                return null;
            }
        };
        ThreadHelper.startThread(task);
        try {
            //等一等上一个线程
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw  new RuntimeException(e);
        }
        //创建新线程 运行主程序
        runAndBuildAction.runProject(runnableClass.get(),
                "-agentlib:jdwp=transport=dt_socket,address=127.0.0.1:8456,suspend=y,server=n");
    }


    public void stopDebug() {
        virtualMachine.exit(0);
    }





    public void nextStep() {

    }


    public CodePosition currentPosition() {
        return null;
    }


    public Map<String, String> fieldValues() {
        return null;
    }


    public Map.Entry<String, String> getFieldValue(String fieldName) {
        return null;
    }


    public String evaluateExpression(String expressions) {
        return null;
    }
}
