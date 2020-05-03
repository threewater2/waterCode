package xyz.threewater.debug;

public class MainClass {

    private String value="value a";

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass=new MainClass();
        System.out.println("debugger started");
        System.out.println("the value is:"+mainClass.value);
        Thread.sleep(5000);
        System.out.println("sleep finished");
    }
}
