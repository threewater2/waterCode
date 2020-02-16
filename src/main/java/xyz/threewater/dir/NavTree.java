package xyz.threewater.dir;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class NavTree extends TreeView<Node> {

    private boolean isEdgeClick=false;

    public NavTree(){
        setStyle("-fx-border-width: 1;-fx-border-color: green");
    }

    public void initTree(TabPane tabPane){
        String path="C:\\Users\\water\\IdeaProjects\\waterIde";
        TreeItem<Node> treeItem=new DirectoryModel(tabPane).getTreeItem(path);
        setRoot(treeItem);
        initEvent();
    }


    private void initEvent(){
        changeCursorStyleAtEdge();
        changeEdgeClickStatus();
        resizeWidth();
    }


    private void changeCursorStyleAtEdge(){
        this.setOnMouseMoved(mouseEvent -> {
            if(isMouseAtEdge(mouseEvent)){
                setCursor(Cursor.E_RESIZE);
            }else {
                setCursor(Cursor.DEFAULT);
            }
        });
    }

    private void changeEdgeClickStatus(){
        this.setOnMousePressed(mouseEvent -> {
            if(isMouseAtEdge(mouseEvent)){
                isEdgeClick=true;
            }
        });
        this.setOnMouseReleased(mouseEvent -> isEdgeClick=false);
    }

    private void resizeWidth(){
        this.setOnMouseDragged(mouseEvent -> {
            if(!isEdgeClick) return;
            setPrefWidth(mouseEvent.getX());
        });
    }

    private boolean isMouseAtEdge(MouseEvent mouseEvent) {
        double cursorX = mouseEvent.getX();
        double componentX = getLayoutBounds().getWidth();
        double scrollWeight = 5;
        return cursorX <= componentX && cursorX > (componentX - scrollWeight);
    }
}
