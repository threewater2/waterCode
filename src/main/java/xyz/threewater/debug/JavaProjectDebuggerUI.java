package xyz.threewater.debug;

import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import xyz.threewater.action.FocusAction;
import xyz.threewater.enviroment.JavaFxComponent;

@Component
public class JavaProjectDebuggerUI {
    private final JavaFxComponent javaFxComponent;
    private final JavaProjectDebugger javaProjectDebugger;
    private final FocusAction focusAction;

    public JavaProjectDebuggerUI(JavaFxComponent javaFxComponent, JavaProjectDebugger javaProjectDebugger, FocusAction focusAction) {
        this.javaFxComponent = javaFxComponent;
        this.javaProjectDebugger = javaProjectDebugger;
        this.focusAction = focusAction;
    }

    public void initialUI(){
        Button debugProjectButton = javaFxComponent.get("debugProjectButton", Button.class);
        //debug 开始按钮
        debugProjectButton.setOnMouseClicked(e->{
           javaProjectDebugger.startDebug();
           focusAction.debugWindowsFocus();
        });
        //debug 下一步按钮
        Button stepOverButton=javaFxComponent.get("stepOverButton",Button.class);
        stepOverButton.setOnMouseClicked(e->{
            javaProjectDebugger.stepOver();
        });
    }
}
