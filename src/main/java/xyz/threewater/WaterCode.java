package xyz.threewater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import xyz.threewater.editor.JavaKeyWordEditor;
import xyz.threewater.utils.PathUtil;
import xyz.threewater.utils.ScreenUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * JavaFX App
 */
public class WaterCode extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setHeight(ScreenUtil.getScreenHeight()*0.8);
        stage.setWidth(ScreenUtil.getScreenWidth()*0.8);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("index.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        scene.getStylesheets().add(JavaKeyWordEditor.class.getResource("java-keywords.css").toExternalForm());
        String imagePath="images"+File.separator+"waterIde.png";
        imagePath= PathUtil.getResourceFromClassPath(imagePath);
        stage.getIcons().add(new Image(new FileInputStream(new File(imagePath))));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}