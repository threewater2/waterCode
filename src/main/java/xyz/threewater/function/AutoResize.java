package xyz.threewater.function;


import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class AutoResize {
    public void resizeRegion(Region parent, Region child){
        child.prefWidthProperty().bind(parent.widthProperty());
        child.prefHeightProperty().bind(parent.heightProperty());
    }
}
