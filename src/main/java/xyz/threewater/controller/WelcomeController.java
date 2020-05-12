package xyz.threewater.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.style.CSSHolder;

import java.io.File;
import java.io.IOException;

@Component
public class WelcomeController {
    @FXML
    public Button config;
    @FXML
    public Button open;
    @FXML
    public Button create;

    private final ProjectEnv projectEnv;

    @Value("classpath:images/logo.png")
    private Resource icon;

    @Value("${window.width}")
    private double width;

    @Value("classpath:xyz/threewater/waterCode.fxml")
    private Resource indexResource;

    @Value("${window.height}")
    private double height;

    private final ApplicationContext applicationContext;
    private final CSSHolder cssFile;
    private final JavaFxComponent javaFxComponent;

    public WelcomeController(ProjectEnv projectEnv, ApplicationContext applicationContext, CSSHolder cssFile, JavaFxComponent javaFxComponent) {
        this.projectEnv = projectEnv;
        this.applicationContext = applicationContext;
        this.cssFile = cssFile;
        this.javaFxComponent = javaFxComponent;
    }

    public void initialize(){
        open.setOnMouseClicked(e-> openDir());
    }


    public void openDir(){
        Stage welcomeStage = javaFxComponent.get("welcomeStage",Stage.class);
        DirectoryChooser directoryChooser=new DirectoryChooser();
        File file = directoryChooser.showDialog(welcomeStage);
        //如果不是的目录就弹窗提示
        if(file==null){
            return;
        }
        if(file.isFile()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"not a directory", ButtonType.OK);
            alert.show();
            return;
        }
        //更改项目配置
        projectEnv.setProjectPath(file.getAbsolutePath());
        try {
            openIndexWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        welcomeStage.close();
    }

    /**
     * 打开主页
     */
    public void openIndexWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(indexResource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent parent=fxmlLoader.load();
        Stage stage = new Stage();
        WaterCodeController controller = fxmlLoader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(parent, width, height);
        scene.getStylesheets().addAll(cssFile.getCss());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(icon.getInputStream()));
        stage.show();
        //add to JavaFxComponent
        javaFxComponent.set("stage",stage);
    }
}
