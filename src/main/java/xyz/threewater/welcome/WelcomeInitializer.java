package xyz.threewater.welcome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.controller.CreateProjectController;
import xyz.threewater.controller.WaterCodeController;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.style.CSSHolder;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class WelcomeInitializer {

    @Value("classpath:images/logo.png")
    private Resource icon;

    @Value("${window.width}")
    private double width;

    @Value("classpath:xyz/threewater/waterCode.fxml")
    private Resource indexResource;

    @Value("classpath:xyz/threewater/createProject.fxml")
    private Resource createResource;

    @Value("${window.height}")
    private double height;

    private final JavaFxComponent javaFxComponent;
    private final ApplicationContext applicationContext;
    private final CSSHolder cssFile;
    private final ProjectEnv projectEnv;


    //jfx component initial
    Stage welcomeStage;
    VBox indexContent;
    VBox createContent;

    public WelcomeInitializer(JavaFxComponent javaFxComponent, ApplicationContext applicationContext, CSSHolder cssFile, ProjectEnv projectEnv) {
        this.javaFxComponent = javaFxComponent;
        this.applicationContext = applicationContext;
        this.cssFile = cssFile;
        this.projectEnv = projectEnv;
    }

    public void initial(){
        //jfx component initial
        welcomeStage=javaFxComponent.get("welcomeStage",Stage.class);
        indexContent = javaFxComponent.get("indexContent", VBox.class);
        createContent = javaFxComponent.get("createContent", VBox.class);
        //创建新项目
        Button create=javaFxComponent.get("create",Button.class);
        create.setOnMouseClicked(e->{
            openCreateProjectPane();
        });
        //打开已有项目事件
        Button open = javaFxComponent.get("open", Button.class);
        open.setOnMouseClicked(e-> openDir());
    }

    private void openDir(){
        Optional<String> projectPath = openFileChooser();
        if(!projectPath.isPresent()){
            showAlert("not a directory");
            return;
        }
        //更改项目配置
        projectEnv.setProjectPath(projectPath.get());
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
    }

    /**
     * 打开创建新项目页面
     */
    private void openCreateProjectPane() {
        TextField projectName = javaFxComponent.get("projectName", TextField.class);
        TextField projectPath = javaFxComponent.get("projectPath", TextField.class);
        Button selectProjectPath = javaFxComponent.get("selectProjectPath", Button.class);
        Button previous = javaFxComponent.get("previous",Button.class);
        Button finish = javaFxComponent.get("finish",Button.class);
        //隐藏主界面
        indexContent.setVisible(false);
        //显示创建项目页面
        createContent.setVisible(true);
        selectProjectPath.setOnMouseClicked(e->{
            Optional<String> filePath = openFileChooser();
            if(!filePath.isPresent()){
                showAlert("not a directory");
            }else {
                projectPath.setText(filePath.get()+File.pathSeparator+projectName);
            }
        });
        //返回按钮
        previous.setOnMouseClicked(e->{
            createContent.setVisible(false);
            indexContent.setVisible(true);
        });
        //创建项目结束按钮
        finish.setOnMouseClicked(e->{
            projectEnv.setProjectName(projectName.getText());
            projectEnv.setProjectPath(projectPath.getText());
            try {
                openIndexWindow();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        });
    }


    private Optional<String> openFileChooser(){
        DirectoryChooser directoryChooser=new DirectoryChooser();
        File file = directoryChooser.showDialog(welcomeStage);
        //如果不是的目录就弹窗提示
        if(file==null){
            return Optional.empty();
        }
        if(file.isDirectory()){
            return Optional.of(file.getAbsolutePath());
        }
        return Optional.empty();
    }

    private void showAlert(String contentText){
        Alert alert=new Alert(Alert.AlertType.INFORMATION,contentText, ButtonType.OK);
        alert.show();
    }
}
