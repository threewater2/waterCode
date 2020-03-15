package xyz.threewater;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.bar.WindowBar;
import xyz.threewater.controller.WaterCodeController;
import xyz.threewater.style.CSSHolder;

import java.io.IOException;

import static xyz.threewater.WaterCodeApplication.*;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final ApplicationContext applicationContext;

    private CSSHolder cssFile;

    private WindowBar windowBar;

    @Value("classpath:xyz/threewater/waterCode.fxml")
    private Resource indexResource;

    @Value("classpath:images/waterCode.png")
    private Resource icon;

    @Value("${window.width}")
    private double width;

    @Value("${window.height}")
    private double height;

    public StageInitializer(ApplicationContext applicationContext, CSSHolder cssFile, WindowBar windowBar) {
        this.applicationContext=applicationContext;
        this.cssFile = cssFile;
        this.windowBar = windowBar;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        //svg支持
        SvgImageLoaderFactory.install();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(indexResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent=fxmlLoader.load();
            WaterCodeController controller = fxmlLoader.getController();
            controller.setStage(stage);
            Scene scene = new Scene(parent, width, height);
            scene.getStylesheets().addAll(cssFile.getCss());
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(icon.getInputStream()));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
