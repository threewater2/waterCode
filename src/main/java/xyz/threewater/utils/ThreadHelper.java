package xyz.threewater.utils;

public class ThreadHelper {
    public static void startThread(Runnable runnable){
        Thread thread=new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }
}
