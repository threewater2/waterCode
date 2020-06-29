package xyz.threewater.function;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursorRecover {

    private static final Logger logger=LoggerFactory.getLogger(CursorRecover.class);

    static void forChild(Region... regions){
       for(Region region:regions){
           region.setOnMouseEntered(e->{
               logger.debug("cursor enter region:{}",region);
               region.setCursor(Cursor.DEFAULT);
           });
       }
    }
}
