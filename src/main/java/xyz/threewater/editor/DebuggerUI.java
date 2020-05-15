package xyz.threewater.editor;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.threewater.action.BreakPointCenter;
import xyz.threewater.action.StepCenter;
import xyz.threewater.utils.SpringUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.IntFunction;

public class DebuggerUI {
    private static final Logger logger= LoggerFactory.getLogger(DebuggerUI.class);


    public static void initial(JavaEditor javaEditor){
        IntFunction<Node> lineNumberFactory = LineNumberFactory.get(javaEditor);
        BreakPointFactory breakPointFactory= new BreakPointFactory(javaEditor);
        RunButtonFactory runButtonFactory=new RunButtonFactory(javaEditor);
        javaEditor.setParagraphGraphicFactory(line -> new HBox(lineNumberFactory.apply(line),
                                                             breakPointFactory.apply(line),
                                                             runButtonFactory.apply(line)));
        StepCenter stepCenter = SpringUtil.getBean(StepCenter.class);
        stepCenter.addStepOverListener((lineNumber, fullClassName, fileName) -> {
            removeLineColor(javaEditor,lineNumber-1);
            addLineColor(javaEditor,lineNumber);
        });
        BreakPointCenter pointCenter = SpringUtil.getBean(BreakPointCenter.class);
        pointCenter.addBreakPointPausedListener(breakPointBean -> addLineColor(javaEditor,breakPointBean.getLine()));
    }

    public static void addLineColor(JavaEditor javaEditor,int line){
        //添加下一行的蓝线
        Paragraph<Collection<String>, String, Collection<String>> paragraph = javaEditor.getParagraph(line-1);
        Collection<String> style = paragraph.getParagraphStyle();
        Collection<String> newStyle = new HashSet<>(style);
        newStyle.add("line-highlight");
        Platform.runLater(() -> javaEditor.setParagraphStyle(line-1,newStyle));
        logger.debug("add step line color at:{} .left{}",line-1,newStyle);
    }

    public static void removeLineColor(JavaEditor javaEditor,int line){
        //删除这一行的蓝线
        Paragraph<Collection<String>, String, Collection<String>> oldParagraph = javaEditor.getParagraph(line-1);
        Collection<String> style = oldParagraph.getParagraphStyle();
        Collection<String> newStyle = new HashSet<>(style);
        logger.debug("remove step line :{}",style);
        newStyle.remove("line-highlight");
        Platform.runLater(()->javaEditor.setParagraphStyle(line-1,newStyle));
        logger.debug("remove step line color at:{} .left {}",line-1,newStyle);
    }
}
