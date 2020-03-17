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

@Component
public class WindowBar {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private boolean isMaximized=false;

    private double deltaX;
    private double deltaY;

    public void initialToolBar(Button minButton, Button closeButton, Button maxButton, Stage stage, Pane titleBar) {
        minButton.setOnMouseClicked(e-> stage.setIconified(true));
        maxButton.setOnMouseClicked(e-> stage.setMaximized(isMaximized=!isMaximized));
        closeButton.setOnMouseClicked(e->Platform.exit());
        //直接调用会使Scene为null
        stage.setOnShown(e-> resizeStage(stage,titleBar));
    }


    public void resizeStage(Stage stage,Pane titleBar){
        StageResize.addResizeListener(stage);
        titleBar.setOnMousePressed(e-> {
            deltaX = stage.getX()-e.getScreenX();
            deltaY = stage.getY()-e.getScreenY();
            titleBar.setCursor(Cursor.MOVE);
            String marker= """

                    title bar mouse pressed
                    delta  [{},{}]
                    stage  [{},{}] sence[{},{}]
                    screen [{},{}]
                    mouse  [{},{}]
                    """;
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
