package xyz.threewater;

import net.bytebuddy.agent.VirtualMachine;


public class TestDebugger {

    private static String field="aa";
    public static void main(String[] args) throws InterruptedException {
        String a="ssss";
        System.out.println(a);
        field="fffff";
        Thread.sleep(100000);

    }
}
