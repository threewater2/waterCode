package xyz.threewater.editor;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public class BreakPointFactory implements IntFunction<Node> {

    private Logger logger= LoggerFactory.getLogger(BreakPointFactory.class);

    private final JavaEditor javaEditor;
    private final Map<Integer,Boolean> isRedMap=new HashMap<>();

    public BreakPointFactory(JavaEditor javaEditor) {
        this.javaEditor = javaEditor;
    }

    @Override
    public Node apply(int lineNumber) {
        isRedMap.putIfAbsent(lineNumber,false);
        Boolean isRed = isRedMap.get(lineNumber);
        HBox hbox=new HBox();
        hbox.getStyleClass().add("editor-leftBar-bg");
        Circle breakPoint=new Circle(9);
        if(isRed){
            breakPoint.setFill(Color.RED);
            breakPoint.setCursor(Cursor.HAND);
        }else {
            breakPoint.setFill(Color.valueOf("#DDDDDD"));
            breakPoint.setCursor(Cursor.DEFAULT);
        }
        breakPoint.setOnMouseClicked(event -> {
            Boolean red = isRedMap.get(lineNumber);
            if(red){
                breakPoint.setFill(Color.valueOf("#DDDDDD"));
                javaEditor.setParagraphStyle(lineNumber, Collections.emptyList());
                breakPoint.setCursor(Cursor.DEFAULT);
                //cancel
                breakPointCancel(javaEditor,lineNumber);
            }else {
                breakPoint.setFill(Color.RED);
                javaEditor.setParagraphStyle(lineNumber,Collections.singleton("break-point"));
                breakPoint.setCursor(Cursor.HAND);
                //add
                breakPointAdd(javaEditor,lineNumber);
            }
            logger.debug("break point clicked isRed:{},lineNumber:{}",red,lineNumber);
            isRedMap.put(lineNumber,!red);
        });

        hbox.getChildren().add(breakPoint);
        return hbox;
    }

    /**
     * 断点取消的回调函数
     */
    public void breakPointCancel(JavaEditor javaEditor,int lineNumber){
        logger.debug("断点取消回调函数被调用了：javaEditor{}, line number{}",javaEditor,lineNumber);
    }

    /**
     * 断点添加时的回调函数
     */
    public void breakPointAdd(JavaEditor javaEditor,int lineNumber){
        logger.debug("断点添加回调函数被调用了：javaEditor{}, line number{}",javaEditor,lineNumber);
    }
}