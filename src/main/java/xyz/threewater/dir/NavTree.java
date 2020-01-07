package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class NavTree extends TreeView<Node> {
    public NavTree(){
        setStyle("-fx-border-width: 1;-fx-border-color: green");
    }

    public void initTree(TabPane tabPane){
        String path="C:\\Users\\water\\OneDrive\\idea_project\\waterIde\\waterIde";
        TreeItem<Node> treeItem=new DirectoryModel(tabPane).getTreeItem(path);
        setRoot(treeItem);
    }
}
