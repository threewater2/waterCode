package xyz.threewater.dir;

import javafx.scene.control.Label;


import java.io.File;

public class FileLabel extends Label {
    private File file;

    public FileLabel(File file,String text){
        super(text);
        this.file=file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
