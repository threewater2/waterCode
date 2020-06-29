package xyz.threewater.utils;

import javafx.stage.Screen;

public class ScreenUtil {
    public static double getScreenWidth(){
        return Screen.getPrimary().getBounds().getWidth();
    }

    public static double getScreenHeight(){
        return Screen.getPrimary().getBounds().getHeight();
    }
}
