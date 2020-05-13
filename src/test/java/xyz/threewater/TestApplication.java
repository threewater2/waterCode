package xyz.threewater;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.fxmisc.richtext.CaretSelectionBind;
import org.fxmisc.richtext.CodeArea;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

//测试保存
//测试保存3
public class TestApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage=new Stage();
        test2(stage);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(TestApplication.class);
    }

    private void test1(Stage stage){
        ListView<String> a=new ListView<>();
        a.getItems().addAll(Arrays.asList("111","111"));
        CodeArea codeArea=new CodeArea();
        Popup popup=new Popup();
        popup.getContent().addAll(a);
        System.out.println("ff");
        codeArea.caretBoundsProperty().addListener((observable, oldValue, newValue) -> {
            double maxX = newValue.get().getMaxX();
            double maxY = newValue.get().getMaxY();
            Platform.exit();
            popup.setX(maxX);
            popup.setX(maxY);
            popup.show(stage);
            System.out.println(maxX);
            System.out.println(maxY);
        });
        codeArea.setOnKeyTyped(e->{
            Platform.exit();
            System.out.println("ddd");
            double maxX = codeArea.getCaretBounds().get().getMaxX();
            double maxY = codeArea.getCaretBounds().get().getMaxY();
            popup.setX(maxX);
            popup.setX(maxY);
            popup.show(stage);
            System.out.println(maxX);
            System.out.println(maxY);
        });
        Scene scene=new Scene(new CodeArea(),400,400);
        stage.setScene(scene);
    }

    private void test2(Stage stage){
        ChoiceBox<String> choiceBox=new ChoiceBox<>();
        choiceBox.getItems().addAll(Arrays.asList("aaaaaa","aaaaaa","qweqweqwewqewqe"));
        BorderPane borderPane=new BorderPane();
        borderPane.setTop(choiceBox);
        Scene scene=new Scene(borderPane,400,400);
        stage.setScene(scene);
    }
}
