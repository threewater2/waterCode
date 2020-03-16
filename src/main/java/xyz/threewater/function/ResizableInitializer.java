package xyz.threewater.function;

import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;


@Component
public class ResizableInitializer {

    private void resizeRegionByPos(Position position, Region... regions){
        WindowResize windowResize=new WindowResize(position);
        for(Region region:regions){
            windowResize.resizable(region);
        }
    }

    public void initial(Region bottom,Region left,Region right) {
        resizeRegionByPos(Position.TOP,bottom);
        resizeRegionByPos(Position.RIGHT,left);
        resizeRegionByPos(Position.LEFT,right);
    }
}
