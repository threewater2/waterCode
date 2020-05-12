package xyz.threewater.enviroment;

import javafx.scene.control.ChoiceBox;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来保存可以运行的java类
 * 存储新形式为全限定类名，绝对路径
 */
@Component
public class MainClassList {
    private final Map<String,String> classNameMap=new HashMap<>();

    private ChoiceBox<String> choiceBox;

    public void initial(ChoiceBox<String> choiceBox){
        this.choiceBox=choiceBox;
    }
    public void addMainClass(String fullName,String classPath){
        this.classNameMap.put(fullName,classPath);
    }

    public String getClassPath(String fullClassName){
        return classNameMap.get(fullClassName);
    }

    public void remove(String fullClassName){
        choiceBox.getItems().remove(fullClassName);
        classNameMap.remove(fullClassName);
    }
}
