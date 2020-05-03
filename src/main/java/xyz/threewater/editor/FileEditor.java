package xyz.threewater.editor;

import javafx.scene.Node;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.threewater.event.FileSaver;
import xyz.threewater.utils.FileUtil;

import java.io.File;

@Component
public class FileEditor {

    private FileSaver saver;
    private AutoCompletion autoCompletion;
    private EventHandler eventHandler;
    private RightClickMenu rightClickMenu;


    public FileEditor(FileSaver saver, AutoCompletion autoCompletion, EventHandler eventHandler, RightClickMenu rightClickMenu) {
        this.autoCompletion=autoCompletion;
        this.saver = saver;
        this.eventHandler = eventHandler;
        this.rightClickMenu = rightClickMenu;
    }

    public Node openFile(File file){
        String text = FileUtil.file2String(file);
        JavaEditor javaEditor= new JavaEditor(text,file.getPath());
        autoCompletion.initial(javaEditor);
        eventHandler.handleMouseClickEvent(javaEditor);
        rightClickMenu.bind(javaEditor);
        saver.addFile(file,javaEditor);
        return new VirtualizedScrollPane<>(javaEditor);
    }

    public void closeFile(File file){
        saver.saveFile();
        saver.removeFile(file);
    }
}
