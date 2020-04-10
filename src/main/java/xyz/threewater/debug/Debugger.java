package xyz.threewater.debug;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Debugger {
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException {
        Debugger debugger=new Debugger();
        VirtualMachine virtualMachine = debugger.initialArguments("localhost", "8456", "10000");
        System.out.println("gg");
    }


    private VirtualMachine initialArguments(String address, String port, String timeout) throws IOException, IllegalConnectorArgumentsException {
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
}
