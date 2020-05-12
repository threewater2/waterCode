package xyz.threewater.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import xyz.threewater.action.FocusAction;
import xyz.threewater.bar.WindowBar;
import xyz.threewater.console.TerminalInitializer;
import xyz.threewater.console.command.CommandLineWindow;
import xyz.threewater.dir.DirectoryInitializer;
//import xyz.threewater.editor.AutoCompletion;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.MainClassList;
import xyz.threewater.function.ResizableInitializer;
import xyz.threewater.plugin.git.GitLogInitializer;
import xyz.threewater.plugin.maven.praser.MavenTreeInitializer;

@Component
public class WaterCodeController {


    @FXML
    public Button rerunProgramButton;
    @FXML
    public ChoiceBox<String> mainClassChoiceBox;
    @FXML
    public ListView codeCompletion;
    @FXML
    public ListView rightClickMenu;
    @FXML
    public Pane root;
    @FXML
    public BorderPane main;
    @FXML
    public Tab gitTab;
    @FXML
    public Tab DebugToolPane;
    @FXML
    public Pane titleBar;
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
    public Button resumeProgramButton;
    @FXML
    public Button stepOverButton;
    @FXML
    public Button stepIntoButton;
    @FXML
    public Button evaluateExpButton;
    @FXML
    public ListView debugVarListView;
    @FXML
    public TextArea debugOutPut;
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

    private DirectoryInitializer directoryInitializer;
    private WindowBar windowBar;
    private MavenTreeInitializer mavenTreeInitializer;
    private TerminalInitializer terminalInitializer;
    private ResizableInitializer resizableInitializer;
    private GitLogInitializer gitLogInitializer;
    private JavaFxComponent javaFxComponent;
    private CommandLineWindow commandLineWindow;
    private FocusAction focusAction;
    private MainClassList mainClassList;
    //    private AutoCompletion autoCompletion;

    public WaterCodeController(DirectoryInitializer directoryInitializer,
                               WindowBar windowBar,
                               MavenTreeInitializer mavenTreeInitializer,
                               TerminalInitializer terminalInitializer,
                               ResizableInitializer resizableInitializer,
                               GitLogInitializer gitLogInitializer,
                               JavaFxComponent javaFxComponent,
                               CommandLineWindow commandLineWindow,
                               FocusAction focusAction,
                               MainClassList mainClassList) {
        this.directoryInitializer = directoryInitializer;
        this.windowBar = windowBar;
        this.mavenTreeInitializer=mavenTreeInitializer;
        this.terminalInitializer=terminalInitializer;
        this.resizableInitializer = resizableInitializer;
        this.gitLogInitializer=gitLogInitializer;
        this.javaFxComponent=javaFxComponent;
        this.commandLineWindow=commandLineWindow;
        this.focusAction=focusAction;
        this.mainClassList=mainClassList;
//        this.autoCompletion=autoCompletion;
    }

    /**
     * 初始化各个组件
     */
    public void initialize(){
        //初始化文件目录树
        TreeItem<Node> treeItem = directoryInitializer.getTreeItem(editorTabPane);
        dirTree.setRoot(treeItem);
        mavenTreeInitializer.initialize(mavenTree,output,bottomTabPane);
        //outPut 输出绑定 命令行输出
        commandLineWindow.makeCommandLineWindow(output);
        //action 初始化
        focusAction.initial(bottomTabPane);
        mainClassList.initial(mainClassChoiceBox);
        //高度和宽度跟随父类
        //当stage准备好的时候
        onStageReady();
        //add to JavaFxComponent
        addJavaFxComponent();
    }

    public void onStageReady(){
        //初始化标题栏事件
        stageInitialized.addListener((observable, oldValue, newValue) ->
                windowBar.initialToolBar(minButton,closeButton,maxButton,stage,titleBar));
        //Java伪终端初始化
        terminalInitializer.initialize(terminalAnchorPane,terminalTabPane,addTerminalButton);
        //窗口拖拽初始化
        resizableInitializer.initial(root,main,bottomTabPane,leftPane,rightTabPane,
                terminalTabPane,mavenTree,dirTree,leftToolBar);
        //初始化git面板
        gitLogInitializer.initial(gitTab);
        //初始化代码提示组件
//        autoCompletion.setCodeCompletion(codeCompletion);
    }

    public void setStage(Stage stage) {
        this.stage=stage;
        //stage初始化完毕
        stageInitialized.setValue(true);
    }


    //getter setter

    public ListView getCodeCompletion() {
        return codeCompletion;
    }

    public void setCodeCompletion(ListView codeCompletion) {
        this.codeCompletion = codeCompletion;
    }

    private void addJavaFxComponent(){
        javaFxComponent.set("codeCompletion",codeCompletion);
        javaFxComponent.set("rightClickMenu",rightClickMenu);
        javaFxComponent.set("dirTree",dirTree);
    }
}
