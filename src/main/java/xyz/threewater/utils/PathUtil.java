package xyz.threewater.utils;

public class PathUtil {
    public static String getClassPath(){
        return PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    public static String getResourceFromClassPath(String filePath){
        return getClassPath()+filePath;
    }
}
