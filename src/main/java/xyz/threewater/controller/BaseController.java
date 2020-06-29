package xyz.threewater.controller;

import javafx.fxml.FXML;
import xyz.threewater.enviroment.JavaFxComponent;

import java.lang.reflect.Field;

/**
 * 他是所有Controller的父类
 * 所有Controller都有往JavaFxComponent中添加组件的方法。
 */
public class BaseController {

    /**
     * 往javaFxComponent中添加组件
     */
    protected void addJavaFxComponent(JavaFxComponent javaFxComponent){
        Class<? extends BaseController> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            FXML annotation = field.getAnnotation(FXML.class);
            if(annotation==null) continue;
            String name = field.getName();
            Object value;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            javaFxComponent.set(name,value);
        }
    }
}
