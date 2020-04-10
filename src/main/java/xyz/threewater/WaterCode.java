package xyz.threewater;

import com.sun.jdi.Bootstrap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class WaterCode extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void init() {
        applicationContext= new SpringApplicationBuilder(WaterCode.class).run();
    }

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage primaryStage) {
            super(primaryStage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }

    public static void main(String[] args) {
        Application.launch(WaterCode.class,args );
    }
}
