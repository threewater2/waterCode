package xyz.threewater.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.welcome.WelcomeInitializer;

@Component
public class WelcomeController {
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

    private final JavaFxComponent javaFxComponent;
    private final WelcomeInitializer welcomeInitializer;
    private Stage welcomeStage;

    public WelcomeController(WelcomeInitializer welcomeInitializer, JavaFxComponent javaFxComponent) {
        this.welcomeInitializer = welcomeInitializer;
        this.javaFxComponent = javaFxComponent;
    }


    public void initialize(){

    }

    private void addJfxComponent(){
        javaFxComponent.set("welcomeStage",welcomeStage);
        javaFxComponent.set("open",open);
        javaFxComponent.set("create",create);
        javaFxComponent.set("config",config);
        javaFxComponent.set("indexContent",indexContent);
        javaFxComponent.set("projectName",projectName);
        javaFxComponent.set("createContent",createContent);
        javaFxComponent.set("projectPath",projectPath);
        javaFxComponent.set("selectProjectPath",selectProjectPath);
        javaFxComponent.set("previous",previous);
        javaFxComponent.set("finish",finish);
    }

    public void onStageReady(Stage stage){
        welcomeStage=stage;
        addJfxComponent();
        welcomeInitializer.initial();
    }
}
