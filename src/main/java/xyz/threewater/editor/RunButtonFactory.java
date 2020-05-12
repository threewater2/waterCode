package xyz.threewater.editor;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.IntFunction;

/**
 * 点击时运行项目，双击时调试项目
 */
public class RunButtonFactory implements IntFunction<Node> {

    private Logger logger= LoggerFactory.getLogger(RunButtonFactory.class);
    private final JavaEditor javaEditor;

    public RunButtonFactory(JavaEditor javaEditor) {
        this.javaEditor = javaEditor;
    }

    @Override
    public Node apply(int lineNumber) {
        //run button
        HBox hbox=new HBox();
        hbox.getStyleClass().add("editor-leftBar-bg");
        Polygon run = new Polygon(0.0, 0.0, 18.0, 9.0, 0.0, 18.0);
        String text=lineNumber>0?javaEditor.getText(lineNumber):"";
        String main="public static void main(String[]";
        if(text.contains(main)&&!text.startsWith("//")){
            run.setFill(Color.GREEN);
            run.setCursor(Cursor.HAND);
            run.setOnMouseClicked(e->{
                //运行按钮单击事件发生了
                if(e.getButton()== MouseButton.PRIMARY){
                    logger.debug("left run button clicked!");
                    runClicked(javaEditor);
                }
            });
        }else {
            run.setFill(Color.valueOf("#DDDDDD"));
        }
        hbox.getChildren().add(run);
        return hbox;
    }


    public void runClicked(JavaEditor javaEditor){
        logger.debug("运行回调函数被调用了：javaEditor{}",javaEditor);
    }
}