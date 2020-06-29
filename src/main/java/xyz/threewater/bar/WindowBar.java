package xyz.threewater.bar;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.function.StageResize;

/**
 * 堆窗口标题栏的操作
 * 包括对窗口的拖动操作，对窗口的三个按钮，最大化最小化和关闭按钮的初始化
 */
@Component
public class WindowBar {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private boolean isMaximized=false;
    private double deltaX;
    private double deltaY;

    /**
     * 初始化窗口标题栏，包括窗口拖拽，窗口最大化，最小化和关闭操作
     * @param minButton 最小化按钮
     * @param closeButton 关闭按钮
     * @param maxButton 最大化按钮
     * @param stage 当前页面的stage
     * @param titleBar 窗口上面的空白处
     */
    public void initialToolBar(Button minButton, Button closeButton, Button maxButton, Stage stage, Pane titleBar) {
        initialWindowButton(stage,minButton,closeButton,maxButton);
        //直接调用会使Scene为null
        stage.setOnShown(e-> resizeStage(stage,titleBar));
    }

    /**
     * 对最大化，最小化，关闭按钮进行初始化
     * @param stage 当前页面的stage
     * @param minButton 最小化按钮
     * @param closeButton 关闭按钮
     * @param maxButton 最大化按钮
     */
    public void initialWindowButton(Stage stage,Button minButton, Button closeButton, Button maxButton){
        if(minButton!=null){
            minButton.setOnMouseClicked(e-> stage.setIconified(true));
        }
        if(maxButton!=null){
            maxButton.setOnMouseClicked(e-> stage.setMaximized(isMaximized=!isMaximized));
        }
        if(closeButton!=null){
            closeButton.setOnMouseClicked(e->{
                Platform.exit();
                System.exit(0);
            });
        }
    }

    /**
     * 支持对窗口的拖拽，窗口大调整
     * @param stage 当前页面的stage
     * @param titleBar 窗口所有的按钮
     */
    public void resizeStage(Stage stage,Pane titleBar){
        StageResize.addResizeListener(stage);
        titleBar.setOnMousePressed(e-> {
            deltaX = stage.getX()-e.getScreenX();
            deltaY = stage.getY()-e.getScreenY();
            titleBar.setCursor(Cursor.MOVE);
            String marker= "\n" +
                           "title bar mouse pressed\n" +
                           "delta  [{},{}]\n" +
                           "stage  [{},{}] sence[{},{}]\n" +
                           "screen [{},{}]\n" +
                           "mouse  [{},{}]\n";
            logger.debug(marker,deltaX,deltaY,stage.getX(),stage.getY(),stage.getScene().getX(),stage.getScene().getY(),e.getScreenX(),e.getScreenY(),e.getX(),e.getY());
        });
        titleBar.setOnMouseReleased(e->{
            titleBar.setCursor(Cursor.DEFAULT);
            logger.debug("title bar mouse released");
        });
        titleBar.setOnMouseDragged(e->{
            logger.debug("title bar mouse moved");
            stage.setX(e.getScreenX()+deltaX);
            stage.setY(e.getScreenY()+deltaY);
        });
    }
}
