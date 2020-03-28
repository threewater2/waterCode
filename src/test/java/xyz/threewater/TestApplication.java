package xyz.threewater;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

//测试保存
//测试保存2
public class TestApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage=new Stage();
        TextArea textArea=new TextArea();
        Scene scene=new Scene(textArea);
        stage.setScene(scene);
        stage.show();
    }
}
