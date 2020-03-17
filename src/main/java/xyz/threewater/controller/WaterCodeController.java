package xyz.threewater.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import xyz.threewater.bar.WindowBar;
import xyz.threewater.console.TerminalInitializer;
import xyz.threewater.dir.DirectoryInitializer;
import xyz.threewater.function.ResizableInitializer;
import xyz.threewater.plugin.maven.praser.MavenTreeInitializer;

@Component
public class WaterCodeController {


    private DirectoryInitializer directoryInitializer;
    private WindowBar windowBar;
    private MavenTreeInitializer mavenTreeInitializer;
    private TerminalInitializer terminalInitializer;
    private ResizableInitializer resizableInitializer;

    @FXML
    public HBox leftToolBar;
    @FXML
    public VBox leftPane;
    @FXML
    public TabPane rightTabPane;
    @FXML
    public TabPane bottomTabPane;
    @FXML
    public TabPane terminalTabPane;
    @FXML
    public AnchorPane terminalAnchorPane;
    @FXML
    public Button addTerminalButton;
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
                               MavenTreeInitializer mavenTreeInitializer,
                               TerminalInitializer terminalInitializer,
                               ResizableInitializer resizableInitializer) {
        this.directoryInitializer = directoryInitializer;
        this.windowBar = windowBar;
        this.mavenTreeInitializer=mavenTreeInitializer;
        this.terminalInitializer=terminalInitializer;
        this.resizableInitializer = resizableInitializer;
    }

    /**
     * 初始化各个组件
     */
    public void initialize(){
        //初始化文件目录树
        TreeItem<Node> treeItem = directoryInitializer.getTreeItem(editorTabPane);
        dirTree.setRoot(treeItem);
        mavenTreeInitializer.initialize(mavenTree,output);
        //高度和宽度跟随父类
        //当stage准备好的时候
        onStageReady();
    }

    public void onStageReady(){
        //初始化标题栏事件
        stageInitialized.addListener((observable, oldValue, newValue) ->
                windowBar.initialToolBar(minButton,closeButton,maxButton,stage));
        //Java伪终端初始化
        terminalInitializer.initialize(terminalAnchorPane,terminalTabPane,addTerminalButton);
        //窗口拖拽初始化
        resizableInitializer.initial(bottomTabPane,leftPane,rightTabPane,
                terminalTabPane,mavenTree,dirTree,leftToolBar);
    }

    public void setStage(Stage stage) {
        this.stage=stage;
        //stage初始化完毕
        stageInitialized.setValue(true);
    }
}
