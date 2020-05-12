package xyz.threewater.editor;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public class DebuggerUI {
    private static Logger logger= LoggerFactory.getLogger(DebuggerUI.class);


    public static void initial(JavaEditor JavaEditor){
        //设置某行高亮
        JavaEditor.setParagraphStyle(19, Collections.singleton("line-highlight"));
        IntFunction<Node> lineNumberFactory = LineNumberFactory.get(JavaEditor);
        BreakPointFactory breakPointFactory= new BreakPointFactory(JavaEditor);
        RunButtonFactory runButtonFactory=new RunButtonFactory(JavaEditor);
        JavaEditor.setParagraphGraphicFactory(line -> new HBox(lineNumberFactory.apply(line),
                                                             breakPointFactory.apply(line),
                                                             runButtonFactory.apply(line)));
    }
}
