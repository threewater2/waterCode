package xyz.threewater.action;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;

/**
 * 当需要focus某个窗口的时候，就用则个触发
 */
@Component
public class FocusAction {
    private final JavaFxComponent javaFxComponent;
    private TabPane bottomTabPane;

    public FocusAction(JavaFxComponent javaFxComponent) {
        this.javaFxComponent = javaFxComponent;
    }

    public void initial(){
        this.bottomTabPane=javaFxComponent.get("bottomTabPane",TabPane.class);
    }

    public void outPutWindowsFocus(){
        focusPane(1);
    }

    public void debugWindowsFocus(){
        focusPane(3);
    }

    private void focusPane(int index){
        SingleSelectionModel<Tab> selectionModel = bottomTabPane.getSelectionModel();
        selectionModel.select(index);
    }
}
