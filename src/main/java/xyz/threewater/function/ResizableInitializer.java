package xyz.threewater.function;

import javafx.scene.layout.Pane;
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

    public void initial(Pane root,Pane main,
                        Region bottom, Region left, Region right,
                        Region terminal, Region mavenTreeView, Region dirTreeView, Region leftBar) {
        dragResizable(Position.TOP,bottom);
        dragResizable(Position.RIGHT,left);
        dragResizable(Position.LEFT,right);

        //follow parent size
        autoResize.bindToParentSize(root,main,2);
        //make it at center
        main.setLayoutX(1);
        main.setLayoutY(1);
        autoResize.bindToParentSize(bottom,terminal);
        autoResize.bindToParentSize(right,mavenTreeView);
        autoResize.bindToParentSize(left,dirTreeView);
        //cursor recover
        CursorRecover.forChild(terminal,mavenTreeView,dirTreeView,leftBar);
    }
}
