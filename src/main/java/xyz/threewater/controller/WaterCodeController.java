package xyz.threewater.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import xyz.threewater.dir.DirectoryInitializer;

@Component
public class WaterCodeController {

    private DirectoryInitializer directoryInitializer;

    @FXML
    private TreeView<Node> dirTree;
    @FXML
    private TabPane editorTabPane;

    public WaterCodeController(DirectoryInitializer directoryInitializer) {
        this.directoryInitializer = directoryInitializer;
    }

    /**
     * 初始化各个组件
     */
    public void initialize(){
        //初始化文件目录树
        TreeItem<Node> treeItem = directoryInitializer.getTreeItem(editorTabPane);
        dirTree.setRoot(treeItem);
    }
}
