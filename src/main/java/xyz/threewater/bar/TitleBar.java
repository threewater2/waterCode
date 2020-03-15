package xyz.threewater.bar;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class TitleBar extends ToolBar {
    public TitleBar() {
        setStyle("-fx-border-width: 1;-fx-border-color: red");
//        setHeight(20);
    }

    public void initTitleBar(){
        ObservableList<Node> items = getItems();
        items.add(new Button("File"));
        items.add(new Button("Edit"));
        items.add(new Button("View"));
        items.add(new Button("Build"));
        items.add(new Button("Run"));
        Circle circle=new Circle(8);
        circle.setFill(Color.RED);
    }
}
