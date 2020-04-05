package xyz.threewater.editor;

import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;

import java.util.Arrays;
import java.util.Optional;

/**
 * 候选列表
 */
@Component
public class AutoCompletion {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private JavaFxComponent javaFxComponent;

    private Stage stage;

    private ListView<String> listView;

    public AutoCompletion(JavaFxComponent javaFxComponent) {
        this.javaFxComponent = javaFxComponent;
    }

    public void initial(CodeArea codeArea){
        showPopup(codeArea);
    }

    @SuppressWarnings("unchecked")
    public void showPopup(CodeArea codeArea){
        stage=javaFxComponent.get("stage",Stage.class);
        listView = javaFxComponent.get("codeCompletion", ListView.class);
        listView.getItems().addAll(Arrays.asList("111","11111"));
        initialKeyEvent(codeArea);
    }


    private void initialKeyEvent(CodeArea codeArea){
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            boolean visible = listView.isVisible();
            if(listView.isVisible()){
                if(KeyCode.ENTER==event.getCode()&&visible){
                    String firstTip = listView.getItems().get(0);
                    codeArea.insertText(codeArea.getCaretPosition(),firstTip);
                    listView.setVisible(false);
                    event.consume();
                }
                int index=0;
                if(KeyCode.UP==event.getCode()&&visible){
                    if(listView.getItems().size()!=0){
                        index=listView.getItems().size()-1;
                    }
                    listView.getSelectionModel().select(index);
                    listView.requestFocus();
                    event.consume();
                }
                if(KeyCode.DOWN==event.getCode()&&visible){
                    if(listView.getItems().size()!=0){
                        index=1;
                    }
                    listView.getSelectionModel().select(index);
                    listView.requestFocus();
                    event.consume();
                }

            }
        });
        codeArea.setOnKeyPressed(event -> {
            Optional<Bounds> caretBounds = codeArea.getCaretBounds();
            KeyCode code = event.getCode();
            if(KeyCode.ESCAPE==code){
                listView.setVisible(false);
                event.consume();
                return;
            }
            if(KeyCode.ENTER==code&&!listView.isVisible()){
                return;
            }
            if(KeyCode.BACK_SPACE==code){
                listView.setVisible(false);
                return;
            }
            if(code.isFunctionKey()||
               code.isWhitespaceKey()||
               code.isMediaKey()||
               code.isModifierKey()||
               code.isArrowKey()|| KeyCode.TAB==code) {
                return;
            }
            if(caretBounds.isPresent()){
                double maxX = caretBounds.get().getMaxX();
                double maxY = caretBounds.get().getMaxY();
                logger.debug("caret x,y:[{},{}]",maxX,maxY);
                logger.debug("codeArea input word:{}",event.getCode().getName());
                listView.setLayoutX(maxX-stage.getX());
                listView.setLayoutY(maxY-stage.getY());
                listView.setVisible(true);
                listView.getSelectionModel().select(0);
            }
        });
        listView.setOnKeyPressed(e->{
            if(KeyCode.ESCAPE==e.getCode()&&listView.isFocused()){
                listView.setVisible(false);
                codeArea.requestFocus();
            }
            if(KeyCode.ENTER==e.getCode()&&listView.isFocused()){
                String selected = listView.getFocusModel().focusedItemProperty().get();
                codeArea.insertText(codeArea.getCaretPosition(),selected);
                listView.setVisible(false);
                codeArea.requestFocus();
            }
        });
    }
}
