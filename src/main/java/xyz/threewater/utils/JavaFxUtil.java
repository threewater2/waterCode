package xyz.threewater.utils;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.threewater.enviroment.JavaFxComponent;

import java.util.Optional;

public class JavaFxUtil {

    private static final Logger logger= LoggerFactory.getLogger(JavaFxUtil.class);
    /**
     * 把一个可以移动的控件移动到鼠标位置
     * @param mouseEvent
     * @param node
     */
    public static void moveToMousePosition(MouseEvent mouseEvent, Node node){
        JavaFxComponent javaFxComponent = SpringUtil.getBean(JavaFxComponent.class);
        Stage stage=javaFxComponent.get("stage", Stage.class);
        double x = mouseEvent.getScreenX();
        double y = mouseEvent.getScreenY();
        node.setLayoutX(x-stage.getX());
        node.setLayoutY(y-stage.getY());
        node.setVisible(true);
        logger.debug("mouseEvent:{x,y}:{},{}",x,y);
    }

    public static Dialog<ButtonType> showDialog(Node node){
        Dialog<ButtonType> dialog=new Dialog<>();
        ButtonType buttonType=new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.getDialogPane().setContent(node);
        return dialog;
    }
}
