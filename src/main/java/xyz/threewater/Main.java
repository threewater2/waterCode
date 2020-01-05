package xyz.threewater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import xyz.threewater.bar.TitleBar;
import xyz.threewater.console.Console;
import xyz.threewater.dir.NavTree;
import xyz.threewater.editor.Editor;
import xyz.threewater.plugin.maven.MavenPlugin;
import xyz.threewater.utils.PathUtil;
import xyz.threewater.utils.ScreenUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * JavaFX App
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setHeight(ScreenUtil.getScreenHeight()*0.8);
        stage.setWidth(ScreenUtil.getScreenWidth()*0.8);
        BorderPane indexPane=new BorderPane();
        indexPane.setTop(new TitleBar());
        indexPane.setLeft(new NavTree());
        indexPane.setBottom(new Console());
        indexPane.setCenter(new Editor());
        indexPane.setRight(new MavenPlugin());
        stage.setScene(new Scene(indexPane));
        String imagePath="images"+File.separator+"waterIde.png";
        imagePath= PathUtil.getResourceFromClassPath(imagePath);
        stage.getIcons().add(new Image(new FileInputStream(new File(imagePath))));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}