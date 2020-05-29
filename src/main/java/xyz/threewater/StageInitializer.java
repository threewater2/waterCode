package xyz.threewater;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import de.codecentric.centerdevice.javafxsvg.dimension.AttributeDimensionProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.bar.WindowBar;
import xyz.threewater.controller.WelcomeController;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.style.CSSHolder;

import java.io.IOException;

import static xyz.threewater.WaterCode.*;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final ApplicationContext applicationContext;

    private CSSHolder cssFile;

    private JavaFxComponent component;



    @Value("classpath:xyz/threewater/welcome.fxml")
    private Resource welcomeResource;



    public StageInitializer(ApplicationContext applicationContext, CSSHolder cssFile, WindowBar windowBar, JavaFxComponent component) {
        this.applicationContext=applicationContext;
        this.cssFile = cssFile;
        this.component = component;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        //svg支持,解决svg锯齿问题
        SvgImageLoaderFactory.install(new AttributeDimensionProvider());
        try {
            openWelcome(stageReadyEvent.getStage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 打开欢迎页
     */
    public void openWelcome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(welcomeResource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent root1 = fxmlLoader.load();
        WelcomeController controller = fxmlLoader.getController();
        controller.onStageReady(stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Water Code");
        stage.setScene(new Scene(root1,600,400));
        stage.show();
    }


}
