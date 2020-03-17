package xyz.threewater.function;

import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;


@Component
public class ResizableInitializer {

    private AutoResize autoResize;

    public ResizableInitializer(AutoResize autoResize){
        this.autoResize=autoResize;
    }

    private void dragResizable(Position position, Region... regions){
        DragResize dragResize =new DragResize(position);
        for(Region region:regions){
            dragResize.resizable(region);
        }
    }

    public void initial(Region bottom,Region left,Region right,
                        Region terminal,Region mavenTreeView,Region dirTreeView,Region leftBar) {
        dragResizable(Position.TOP,bottom);
        dragResizable(Position.RIGHT,left);
        dragResizable(Position.LEFT,right);

        //auto
       autoResize.resizeRegion(bottom,terminal);
       autoResize.resizeRegion(right,mavenTreeView);
       autoResize.resizeRegion(left,dirTreeView);
       //cursor recover
       CursorRecover.forChild(terminal,mavenTreeView,dirTreeView,leftBar);
    }
}
