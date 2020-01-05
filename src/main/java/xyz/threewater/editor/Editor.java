package xyz.threewater.editor;

import javafx.scene.control.TextArea;

public class Editor extends TextArea {
    public Editor(){
        initStyle();
    }


    private void initStyle(){
        this.setStyle("-fx-border-width: 1;-fx-border-color: black");
    }

}
