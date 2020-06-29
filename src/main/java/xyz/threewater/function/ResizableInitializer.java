package xyz.threewater.function;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;


@Component
public class ResizableInitializer {

    private final AutoResize autoResize;

    private final JavaFxComponent javaFxComponent;

    public ResizableInitializer(AutoResize autoResize, JavaFxComponent javaFxComponent){
        this.autoResize=autoResize;
        this.javaFxComponent = javaFxComponent;
    }

    private void dragResizable(Position position, Region... regions){
        DragResize dragResize =new DragResize(position);
        for(Region region:regions){
            dragResize.resizable(region);
        }
    }

    public void initial() {
        //root,main,bottomTabPane,leftPane,rightTabPane,
        //                terminalTabPane,mavenTree,dirTree,leftToolBar
        Pane root = javaFxComponent.get("root", Pane.class);
        Pane main = javaFxComponent.get("main", Pane.class);
        Region bottom = javaFxComponent.get("bottomTabPane", Region.class);
        Region left = javaFxComponent.get("leftPane", Region.class);
        Region right = javaFxComponent.get("rightTabPane", Region.class);
        Region terminal = javaFxComponent.get("terminalTabPane", Region.class);
        Region mavenTreeView = javaFxComponent.get("mavenTree", Region.class);
        Region dirTreeView = javaFxComponent.get("dirTree", Region.class);
        Region leftBar = javaFxComponent.get("leftToolBar", Region.class);

        dragResizable(Position.TOP, bottom);
        dragResizable(Position.RIGHT, left);
        dragResizable(Position.LEFT, right);

        //follow parent size
        autoResize.bindToParentSize(root, main,2);
        //make it at center
        main.setLayoutX(1);
        main.setLayoutY(1);
        autoResize.bindToParentSize(bottom, terminal);
        autoResize.bindToParentSize(right, mavenTreeView);
        autoResize.bindToParentSize(left, dirTreeView);
        //cursor recover
        CursorRecover.forChild(terminal, mavenTreeView, dirTreeView, leftBar);
    }
}
