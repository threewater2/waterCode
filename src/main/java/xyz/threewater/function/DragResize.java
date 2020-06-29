package xyz.threewater.function;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class DragResize {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private final Position resizePosition;

    //每一个Region都有自己的拖拽状态
    private Map<Region,Boolean> dragEdge =new HashMap<>();

    public DragResize(Position resizePosition) {
        this.resizePosition = resizePosition;
    }


    public void resizable(Region region){
        region.setOnMousePressed(e->{
            if(isAtEdge(region,e)){
                dragEdge.putIfAbsent(region,true);
                logger.debug("edge press value:{}", true);
            }
        });
        region.setOnMouseReleased(e->{
            if(dragEdge.getOrDefault(region,false)){
                region.setCursor(Cursor.DEFAULT);
                dragEdge.putIfAbsent(region,false);
                logger.debug("edge release value:{}", false);
            }
        });
        region.setOnMouseExited(e->{
            if(!dragEdge.getOrDefault(region,false)){
                region.setCursor(Cursor.DEFAULT);
                logger.debug("edge exited");
            }
            logger.debug("edge exited2");
        });
        region.setOnMouseMoved(e->{
            logger.debug("event target:{}",e.getTarget());
            if(isAtEdge(region,e)){
                changeCursor(region);
                logger.debug("cursor at edge:[{},{}] [{},{}]",e.getX(),e.getY(),region.getWidth(),region.getHeight());
            }else {
                region.setCursor(Cursor.DEFAULT);
                logger.debug("cursor not at edge");
            }
        });
        region.setOnMouseDragged(e->{
            logger.debug("edge move value:{}", dragEdge.getOrDefault(region,false));
            if(dragEdge.getOrDefault(region,false)) {
                switch (resizePosition) {
                    case TOP:
                        region.setPrefHeight(region.getHeight() - e.getY());
                        break;
                    case BOTTOM:
                        region.setPrefHeight(e.getY());
                        break;
                    case LEFT:
                        region.setPrefWidth(region.getWidth() - e.getX());
                        break;
                    case RIGHT:
                        region.setPrefWidth(e.getX());
                        break;
                }
            }
        });
        //当鼠标进入儿子节点时恢复
        CursorRecover.forChild(region);
    }

    private void changeCursor(Region region){
        switch (resizePosition){
            case TOP:
            case BOTTOM:
                region.setCursor(Cursor.N_RESIZE);
                break;
            case LEFT:
            case RIGHT:
                region.setCursor(Cursor.W_RESIZE);
                break;
            case CENTER:
                region.setCursor(Cursor.DEFAULT);
                break;
        }
    }

    private boolean isAtEdge(Region region, MouseEvent mouseEvent){
        double y = mouseEvent.getY();
        double x = mouseEvent.getX();
        double height = region.getHeight();
        double width = region.getWidth();
        double margin = 5;
        switch (resizePosition){
            case TOP:
                return y < margin;
            case BOTTOM:
                return y<height&&y>(height- margin);
            case LEFT:
                return x < margin;
            case RIGHT :
                return x < width && x > (width - margin);
        }
        return false;
    }

}
