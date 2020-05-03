package xyz.threewater.editor;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;

import java.io.File;
import java.util.Optional;

@Component
public class EventHandler {

    JavaFxComponent javaFxComponent;

    public EventHandler(JavaFxComponent javaFxComponent) {
        this.javaFxComponent = javaFxComponent;
    }

    @SuppressWarnings("unchecked")
    public void handleMouseClickEvent(JavaEditor javaEditor){
        javaEditor.setOnMouseClicked(mouseEvent->{
            ListView<String> rightClickMenu = javaFxComponent.get("rightClickMenu", ListView.class);
            if(mouseEvent.getButton()== MouseButton.SECONDARY){
                showPopupAtCodeArea(javaEditor,rightClickMenu);
            }
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                rightClickMenu.setVisible(false);
            }
        });
    }

    private void showPopupAtCodeArea(CodeArea codeArea, Node node){
        Stage stage=javaFxComponent.get("stage", Stage.class);
        Optional<Bounds> caretBounds = codeArea.getCaretBounds();
        if(!caretBounds.isPresent()) return;
        double maxX = caretBounds.get().getMaxX();
        double maxY = caretBounds.get().getMaxY();
        node.setLayoutX(maxX-stage.getX());
        node.setLayoutY(maxY-stage.getY());
        node.setVisible(true);
    }
}
