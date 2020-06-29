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
import xyz.threewater.debug.JavaProjectDebuggerUI;
import xyz.threewater.dir.DirectoryInitializer;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.MainClassList;
import xyz.threewater.event.MouseEventInitializer;
import xyz.threewater.function.ResizableInitializer;
import xyz.threewater.plugin.git.GitLogInitializer;
import xyz.threewater.plugin.maven.MavenPlugin;
import xyz.threewater.run.RunProjectUI;

@Component
public class WaterCodeController extends BaseController{

    @FXML
    public Button rerunProgramButton;
    @FXML
    public ListView<Node> codeCompletion;
    @FXML
    public ListView<Node> rightClickMenu;
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
    public ListView<String> debugVarListView;
    @FXML
    public TextArea debugOutPut;
    @FXML
    public Button mainClassButton;
    @FXML
    public VBox mainClassGroup;
    @FXML
    public Button runProjectButton;
    @FXML
    public Button debugProjectButton;
    @FXML
    public VBox dirMenu;
    @FXML
    public Label addFile;
    @FXML
    public Label deleteFile;
    @FXML
    public Label renameFile;
    @FXML
    public Label addDir;
    @FXML
    public TreeView<Node> dirTree;
    @FXML
    public TreeView<Node> mavenTree;
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
    @FXML
    public TabPane editorTabPane;

    private final BooleanProperty stageInitialized =new SimpleBooleanProperty(false);

    private final DirectoryInitializer directoryInitializer;
    private final WindowBar windowBar;
    private final MavenPlugin mavenPlugin;
    private final TerminalInitializer terminalInitializer;
    private final ResizableInitializer resizableInitializer;
    private final GitLogInitializer gitLogInitializer;
    private final JavaFxComponent javaFxComponent;
    private final FocusAction focusAction;
    private final MainClassList mainClassList;
    private final RunProjectUI runProjectUI;
    private final JavaProjectDebuggerUI javaProjectDebuggerUI;
    private final MouseEventInitializer mouseEventInitializer;


    public WaterCodeController(DirectoryInitializer directoryInitializer,
                               WindowBar windowBar,
                               MavenPlugin mavenPlugin, TerminalInitializer terminalInitializer,
                               ResizableInitializer resizableInitializer,
                               GitLogInitializer gitLogInitializer,
                               JavaFxComponent javaFxComponent,
                               FocusAction focusAction,
                               MainClassList mainClassList,
                               RunProjectUI runProjectUI,
                               JavaProjectDebuggerUI javaProjectDebuggerUI,
                               MouseEventInitializer mouseEventInitializer) {
        this.directoryInitializer = directoryInitializer;
        this.windowBar = windowBar;
        this.mavenPlugin = mavenPlugin;
        this.terminalInitializer=terminalInitializer;
        this.resizableInitializer = resizableInitializer;
        this.gitLogInitializer=gitLogInitializer;
        this.javaFxComponent=javaFxComponent;
        this.focusAction=focusAction;
        this.mainClassList=mainClassList;
        this.runProjectUI = runProjectUI;
        this.javaProjectDebuggerUI = javaProjectDebuggerUI;
        this.mouseEventInitializer = mouseEventInitializer;
    }

    /**
     * 初始化各个组件
     */
    public void initialize(){
        //add to JavaFxComponent
        addJavaFxComponent(javaFxComponent);
        //当stage准备好的时候
        onStageReady();
    }

    public void onStageReady(){
        //初始化标题栏事件
        stageInitialized.addListener((observable, oldValue, newValue) -> windowBar.initial());
        //初始化文件目录树
        directoryInitializer.initial();
        //maven依赖树初始化
        mavenPlugin.initial();
        //action 初始化
        focusAction.initial();
        mainClassList.initial();
        //Java伪终端初始化
        terminalInitializer.initial();
        //窗口拖拽初始化
        resizableInitializer.initial();
        //初始化git面板
        gitLogInitializer.initial();
        runProjectUI.initial();
        javaProjectDebuggerUI.initialUI();
        mouseEventInitializer.initial();
    }

    public void setStage(Stage stage) {
        javaFxComponent.set("stage",stage);
        //stage初始化完毕
        stageInitialized.setValue( true);
    }
}
