package xyz.threewater.event;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import xyz.threewater.action.MouseAction;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.utils.JavaFxUtil;

/**
 * 初始化一些空白处的点击事件
 */
@Component
public class MouseEventInitializer {

    private final JavaFxComponent javaFxComponent;
    private final MouseAction mouseAction;

    public MouseEventInitializer(JavaFxComponent javaFxComponent, MouseAction mouseAction) {
        this.javaFxComponent = javaFxComponent;
        this.mouseAction = mouseAction;
    }

    public void initial(){
        mouseBlackClick();
    }

    /**
     * 鼠标空白处被点击应该执行的事件
     */
    private void mouseBlackClick(){
        //目录树空白处点击事件
        TreeView dirTree = javaFxComponent.get("dirTree", TreeView.class);
        dirTree.setOnMouseClicked(mouseAction::blackClick);
    }


}
