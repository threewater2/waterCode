package xyz.threewater.editor;

import javafx.scene.Node;
import javafx.scene.text.Font;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.io.IOException;

@Component
public class FileEditor {

    @Value("classpath:xyz/threewater/editor/JetBrainsMono-Regular.ttf")
    private Resource font;

    public Node openFile(File file){
        String text = FileUtil.file2String(file);
        try {
            Font font = Font.loadFont(this.font.getInputStream(), 18);
            System.out.println(font.getFamily());
            System.out.println(font.getStyle());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CodeArea codeArea= new JavaKeyWordEditor(text,file.getPath());
        return codeArea;
    }
}
