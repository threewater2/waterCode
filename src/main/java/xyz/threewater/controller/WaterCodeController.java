package xyz.threewater.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import xyz.threewater.bar.WindowBar;
import xyz.threewater.dir.DirectoryInitializer;
import xyz.threewater.plugin.maven.praser.MavenTreeInitializer;

@Component
public class WaterCodeController {


    private DirectoryInitializer directoryInitializer;
    private WindowBar windowBar;
    private MavenTreeInitializer mavenTreeInitializer;

    @FXML
    private TreeView<Node> dirTree;
    @FXML
    public TreeView<Node> mavenTree;
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
    @FXML
    public Button pos;
    @FXML
    public Button minimize;
    @FXML
    public Button iconButton;
    @FXML
    public TextArea output;
    private BooleanProperty stageInitialized =new SimpleBooleanProperty(false);
    private Stage stage;

    public WaterCodeController(DirectoryInitializer directoryInitializer,
                               WindowBar windowBar,
                               MavenTreeInitializer mavenTreeInitializer) {
        this.directoryInitializer = directoryInitializer;
        this.windowBar = windowBar;
        this.mavenTreeInitializer=mavenTreeInitializer;
    }

    /**
     * 初始化各个组件
     */
    public void initialize(){
        //初始化文件目录树
        TreeItem<Node> treeItem = directoryInitializer.getTreeItem(editorTabPane);
        dirTree.setRoot(treeItem);
        mavenTreeInitializer.initialize(mavenTree,output);
        //当stage准备好的时候
        onStageReady();
    }

    public void onStageReady(){
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
