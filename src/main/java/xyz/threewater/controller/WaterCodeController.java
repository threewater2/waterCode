package xyz.threewater.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import xyz.threewater.bar.WindowBar;
import xyz.threewater.dir.DirectoryInitializer;

@Component
public class WaterCodeController {


    private DirectoryInitializer directoryInitializer;
    private WindowBar windowBar;

    @FXML
    private TreeView<Node> dirTree;
    @FXML
    private TabPane editorTabPane;
    @FXML
    public HBox toolBar;
    @FXML
    public Button minButton;
    @FXML
    public Button maxButton;
    @FXML
    public Button closeButton;

    private BooleanProperty stageInitialized =new SimpleBooleanProperty(false);
    private Stage stage;

    public WaterCodeController(DirectoryInitializer directoryInitializer, WindowBar windowBar) {
        this.directoryInitializer = directoryInitializer;
        this.windowBar = windowBar;
    }

    /**
     * 初始化各个组件
     */
    public void initialize(){
        //初始化文件目录树
        TreeItem<Node> treeItem = directoryInitializer.getTreeItem(editorTabPane);
        dirTree.setRoot(treeItem);
        //初始化标题栏事件
        stageInitialized.addListener((observable, oldValue, newValue) ->
                windowBar.initialToolBar(minButton,closeButton,maxButton,stage));
    }

    public void setStage(Stage stage) {
        this.stage=stage;
        //stage初始化完毕
        stageInitialized.setValue(true);
    }
}
