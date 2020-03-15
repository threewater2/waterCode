package xyz.threewater.editor;

import javafx.scene.Node;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.utils.FileUtil;

import java.io.File;

@Component
public class FileEditor {

    @Value("classpath:xyz/threewater/editor/JetBrainsMono-Regular.ttf")
    private Resource font;

    public Node openFile(File file){
        String text = FileUtil.file2String(file);
        CodeArea codeArea= new JavaKeyWordEditor(text,file.getPath());
        VirtualizedScrollPane pane=new VirtualizedScrollPane(codeArea);
        return pane;
    }
}
