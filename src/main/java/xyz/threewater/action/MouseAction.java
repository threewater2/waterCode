package xyz.threewater.action;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;

@Component
public class MouseAction {

    private final JavaFxComponent javaFxComponent;

    public MouseAction(JavaFxComponent javaFxComponent) {
        this.javaFxComponent = javaFxComponent;
    }

    public void leftClick(){

    }

    public void rightClick(){

    }

    public void blackClick(MouseEvent mouseEvent){
        VBox dirMenu = javaFxComponent.get("dirMenu", VBox.class);
        if(dirMenu.isVisible()){
            dirMenu.setVisible(false);
        }
    }
}
