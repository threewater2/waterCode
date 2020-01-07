package xyz.threewater.dir;

import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
@Getter
@Setter
public class FileLabel extends Label {
    private File file;

    public FileLabel(File file,String text){
        super(text);
        this.file=file;
    }

}
