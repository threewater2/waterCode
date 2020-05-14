package xyz.threewater.debug;

public class DebugDemo {
    private static String a="static value string";
    private String b="object value string";
    private static int c=1;
    private int d=2;
    private static double e=1.233;
    private double f=1.233;
    private static MyClass myClass=new MyClass();
    private MyClass myClass2=new MyClass();

    public static void main(String[] args) {
        String fieldA="field a";
        int fieldB=2;
        DebugDemo debugDemo=new DebugDemo();
        System.out.println(fieldA+fieldB);
        System.out.println(debugDemo);
        int i = debugDemo.myDemoMethod(1);
        System.out.println(i);
        MainClass mainClass=new MainClass();
    }

    public int myDemoMethod(int value){
        return value;
    }

    @Override
    public String toString() {
        return "DebugDemo{" +
                "b='" + b + '\'' +
                ", d=" + d +
                ", f=" + f +
                ", myClass2=" + myClass2 +
                '}';
    }
}
