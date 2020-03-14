package xyz.threewater;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static xyz.threewater.WaterCodeApplication.*;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final ApplicationContext applicationContext;

    @Value("classpath:xyz/threewater/waterCode.fxml")
    private Resource indexResource;

    @Value("classpath:images/waterCode.png")
    private Resource icon;

    @Value("${window.width}")
    private double width;

    @Value("${window.height}")
    private double height;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext=applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(indexResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent=fxmlLoader.load();
            stage.setScene(new Scene(parent, width, height));
            stage.getIcons().add(new Image(icon.getInputStream()));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
