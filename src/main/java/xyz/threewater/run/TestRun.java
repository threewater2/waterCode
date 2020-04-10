package xyz.threewater.run;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;

public class TestRun {
    public static void main(String[] args) {
        VirtualMachineManager virtualMachine= Bootstrap.virtualMachineManager();
        System.out.println("gg");
    }
}
