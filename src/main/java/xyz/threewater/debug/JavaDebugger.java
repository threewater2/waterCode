package xyz.threewater.debug;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import xyz.threewater.function.Position;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JavaDebugger implements Debugger{

    private VirtualMachine virtualMachine;
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException {
        JavaDebugger javaDebugger =new JavaDebugger();
        VirtualMachine virtualMachine = javaDebugger.initialArguments("localhost", "8456", "10000");
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

    @Override
    public void startDebug(String mainClassPath) {
        try {
            virtualMachine = initialArguments("localhost", "8456", "10000");
        } catch (IllegalConnectorArgumentsException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopDebug() {
        virtualMachine.exit(0);
    }

    @Override
    public void addBreakPoint(CodePosition position) {
        List<ReferenceType> referenceTypes = virtualMachine.classesByName(position.getFullClassName());
        ReferenceType referenceType = referenceTypes.get(0);
        List<Location> locations;
        try {
            locations = referenceType.locationsOfLine(position.getLine());
        } catch (AbsentInformationException e) {
            throw new RuntimeException(e);
        }
        Location location = locations.get(0);
        BreakpointRequest breakpointRequest = virtualMachine.eventRequestManager().createBreakpointRequest(location);
        breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        breakpointRequest.enable();
    }

    @Override
    public void removeBreakPoint(CodePosition position) {
        List<BreakpointRequest> breakpointRequests = virtualMachine.eventRequestManager().breakpointRequests();
        breakpointRequests.forEach(breakpointRequest -> {
            Location location = breakpointRequest.location();
            if(location.declaringType().name().equals(position.getFullClassName())
                &&location.lineNumber()==position.getLine()){
                breakpointRequest.disable();
            }
        });
    }

    @Override
    public void nextStep() {

    }

    @Override
    public CodePosition currentPosition() {
        return null;
    }

    @Override
    public Map<String, String> fieldValues() {
        return null;
    }

    @Override
    public Map.Entry<String, String> getFieldValue(String fieldName) {
        return null;
    }

    @Override
    public String evaluateExpression(String expressions) {
        return null;
    }
}
