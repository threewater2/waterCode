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
import xyz.threewater.editor.parse.SourceCodeKeyWord;
import xyz.threewater.editor.parse.Trie;
import xyz.threewater.enviroment.JavaFxComponent;

import java.util.List;
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

    private Trie<String> trie=new Trie<>();

    private SourceCodeKeyWord sourceCodeKeyWord;

    private InputCache inputCache =new InputCache();

    public AutoCompletion(JavaFxComponent javaFxComponent,SourceCodeKeyWord sourceCodeKeyWord) {
        this.sourceCodeKeyWord=sourceCodeKeyWord;
        this.javaFxComponent = javaFxComponent;
    }

    public void initial(CodeArea codeArea){
        showPopup(codeArea);
    }

    @SuppressWarnings("unchecked")
    public void showPopup(CodeArea codeArea){
        stage=javaFxComponent.get("stage",Stage.class);
        listView = javaFxComponent.get("codeCompletion", ListView.class);
        initialKeyWord(codeArea.getText());
        initialKeyEvent(codeArea);
    }

    private void initialKeyWord(String text) {
        for(String keyWord:sourceCodeKeyWord.getKeyWords(text)){
            trie.add(keyWord,null);
        }
    }

    private void reloadSuggestion(){
        Optional<String> cache = inputCache.getCache();
        if(!cache.isPresent()){
            listView.setVisible(false);
            return;
        }
        List<String> suggestions = trie.startWith(cache.get());
        suggestions.addAll(trie.startWith(cache.get()));
        listView.getItems().removeAll();
        logger.debug("suggestion size:{}",suggestions.size());
        if(suggestions.size()==0){
            suggestions.add("No Suggestions");
        }
        listView.getItems().setAll(suggestions);
        listView.setVisible(true);
    }

    private void initialKeyEvent(CodeArea codeArea){
        //control visible
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            boolean visible = listView.isVisible();
            if(listView.isVisible()){
                if(KeyCode.ENTER==event.getCode()&&visible){
                    insertSelect(codeArea);
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
            //ignore functional key
            KeyCode code = event.getCode();
            if(KeyCode.ESCAPE==code){
                listView.setVisible(false);
                event.consume();
                return;
            }
            //delete word happened
            if(KeyCode.BACK_SPACE==code){
                listView.setVisible(false);
                inputCache.deleteCache();
            }
        });

        codeArea.setOnKeyTyped(event->{
            Optional<Bounds> caretBounds = codeArea.getCaretBounds();
            if(!caretBounds.isPresent()) return;
            String newChar=event.getCharacter();
            //TODO
            if(newChar.equals(".")||newChar.equals(" ")||newChar.equals("\n")||newChar.equals("\r")){
                inputCache.clearCache();
                return;
            }
            if(!isLetterOrDigital(newChar)) return;
            double maxX = caretBounds.get().getMaxX();
            double maxY = caretBounds.get().getMaxY();
            logger.debug("caret x,y:[{},{}]",maxX,maxY);
            logger.debug("codeArea input word:{}",newChar);
            listView.setLayoutX(maxX-stage.getX());
            listView.setLayoutY(maxY-stage.getY());
            inputCache.addCache(event.getCharacter());
            reloadSuggestion();
            listView.getSelectionModel().select(0);
        });
        //select suggestion
        listView.setOnKeyPressed(e->{
            if(KeyCode.ESCAPE==e.getCode()&&listView.isFocused()){
                listView.setVisible(false);
                codeArea.requestFocus();
            }
            //suggestion selected
            if(KeyCode.ENTER==e.getCode()&&listView.isFocused()){
                insertSelect(codeArea);
            }
        });
    }

    private boolean isLetterOrDigital(String str){
        boolean isValid=true;
        for(char c:str.toCharArray()){
            isValid=Character.isLetterOrDigit(c);
        }
        return isValid;
    }

    private void insertSelect(CodeArea codeArea){
        String selected = listView.getFocusModel().focusedItemProperty().get();
        deleteTmpInput(codeArea);
        codeArea.insertText(codeArea.getCaretPosition(),selected);
        listView.setVisible(false);
        codeArea.requestFocus();
        inputCache.clearCache();
    }

    private void deleteTmpInput(CodeArea codeArea){
        Optional<String> cache = inputCache.getCache();
        if(cache.isPresent()){
            int caretPos=codeArea.getCaretPosition();
            logger.debug("tmp text delete:cache{},caret pos:{}",cache.get(),caretPos);
            int size=cache.get().length();
            codeArea.deleteText(codeArea.getCaretPosition()-size,caretPos);
        }
    }
}
