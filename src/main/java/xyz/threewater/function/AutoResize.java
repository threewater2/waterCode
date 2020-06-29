package xyz.threewater.function;


import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class AutoResize {
    public void bindToParentSize(Region parent, Region child){
        child.prefWidthProperty().bind(parent.widthProperty());
        child.prefHeightProperty().bind(parent.heightProperty());
    }

    public void bindToParentSize(Region parent, Region child,double padding){
        parent.widthProperty().addListener((observable, oldValue, newValue) ->
                child.setPrefWidth(newValue.doubleValue()-padding));
        parent.heightProperty().addListener((observable, oldValue, newValue) ->
                child.setPrefHeight(newValue.doubleValue()-padding));
    }
}
