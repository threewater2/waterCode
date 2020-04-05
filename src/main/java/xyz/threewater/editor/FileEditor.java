package xyz.threewater.editor;

import javafx.scene.Node;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.threewater.controller.WaterCodeController;
import xyz.threewater.event.FileSaver;
import xyz.threewater.utils.FileUtil;

import java.io.File;

@Component
public class FileEditor {

    private FileSaver saver;
    private AutoCompletion autoCompletion;


    public FileEditor(FileSaver saver,AutoCompletion autoCompletion) {
        this.autoCompletion=autoCompletion;
        this.saver = saver;
    }

    public Node openFile(File file){
        String text = FileUtil.file2String(file);
        CodeArea codeArea= new JavaKeyWordEditor(text,file.getPath());
        autoCompletion.initial(codeArea);
        saver.addFile(file,codeArea);
        return new VirtualizedScrollPane<>(codeArea);
    }

    public void closeFile(File file){
        saver.saveFile();
        saver.removeFile(file);
    }
}
