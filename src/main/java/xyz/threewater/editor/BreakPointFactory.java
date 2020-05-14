package xyz.threewater.editor;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.threewater.build.SourceCodeAnalysis;
import xyz.threewater.debug.BreakPointHolder;
import xyz.threewater.utils.SpringUtil;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * 这个类需要更改，效率不高，容易重复执行
 */
public class BreakPointFactory implements IntFunction<Node> {

    private final Logger logger= LoggerFactory.getLogger(BreakPointFactory.class);
    private final JavaEditor javaEditor;
    private final Map<Integer,Boolean> isRedMap=new HashMap<>();
    private final SourceCodeAnalysis sourceCodeAnalysis;
    private final BreakPointHolder breakPointHolder= SpringUtil.getBean(BreakPointHolder.class);

    public BreakPointFactory(JavaEditor javaEditor) {
        this.javaEditor = javaEditor;
        this.sourceCodeAnalysis=new SourceCodeAnalysis(new File(javaEditor.getFilePath()));
    }

    @Override
    public Node apply(int lineNumber) {
        String fullClassName = sourceCodeAnalysis.getFullClassName();
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
                breakPointCancel(lineNumber);
            }else {
                breakPoint.setFill(Color.RED);
                javaEditor.setParagraphStyle(lineNumber,Collections.singleton("break-point"));
                breakPoint.setCursor(Cursor.HAND);
                //add
                breakPointAdd(lineNumber);
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
    public void breakPointCancel(int lineNumber){
        //从断点库中取消断点
        breakPointHolder.removeBreakPoint(sourceCodeAnalysis.getFullClassName(),lineNumber);
    }

    /**
     * 断点添加时的回调函数
     */
    public void breakPointAdd(int lineNumber){
        //从断点库中添加断点
        breakPointHolder.addBreakPoint(sourceCodeAnalysis.getFullClassName(),lineNumber);
    }
}