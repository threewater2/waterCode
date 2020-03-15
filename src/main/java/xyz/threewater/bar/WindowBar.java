package xyz.threewater.bar;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
@Component
public class WindowBar {
    private boolean isMaximized=false;

    public void initialToolBar(Button minButton, Button closeButton, Button maxButton, Stage stage) {
        minButton.setOnMouseClicked(e-> stage.setIconified(true));
        maxButton.setOnMouseClicked(e-> stage.setMaximized(isMaximized=!isMaximized));
        closeButton.setOnMouseClicked(e->Platform.exit());
    }
}
