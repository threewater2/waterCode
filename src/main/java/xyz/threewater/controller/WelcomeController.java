package xyz.threewater.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.welcome.WelcomeInitializer;

@Component
public class WelcomeController extends BaseController{
    @FXML
    public Button config;
    @FXML
    public Button open;
    @FXML
    public Button create;
    @FXML
    public VBox indexContent;
    @FXML
    public TextField projectName;
    @FXML
    public VBox createContent;
    @FXML
    public TextField projectPath;
    @FXML
    public Button selectProjectPath;
    @FXML
    public Button previous;
    @FXML
    public Button finish;
    @FXML
    public Button welcomeMinButton;
    @FXML
    public Button welcomeCloseButton;
    @FXML
    public Pane welcomeTitleBar;
    private final JavaFxComponent javaFxComponent;
    private final WelcomeInitializer welcomeInitializer;

    public WelcomeController(WelcomeInitializer welcomeInitializer,
                             JavaFxComponent javaFxComponent) {
        this.welcomeInitializer = welcomeInitializer;
        this.javaFxComponent = javaFxComponent;
    }

    public void initialize(){

    }

    public void onStageReady(Stage stage){
        javaFxComponent.set("welcomeStage", stage);
        addJavaFxComponent(javaFxComponent);
        welcomeInitializer.initial();
    }
}
