package xyz.threewater.debug;

import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import xyz.threewater.action.FocusAction;
import xyz.threewater.action.StepCenter;
import xyz.threewater.action.StepOverListener;
import xyz.threewater.enviroment.JavaFxComponent;

import javax.annotation.Nullable;

@Component
public class JavaProjectDebuggerUI {
    private final JavaFxComponent javaFxComponent;
    private final JavaProjectDebugger javaProjectDebugger;
    private final FocusAction focusAction;
    private final StepCenter stepCenter;

    public JavaProjectDebuggerUI(JavaFxComponent javaFxComponent, JavaProjectDebugger javaProjectDebugger, FocusAction focusAction, StepCenter stepCenter) {
        this.javaFxComponent = javaFxComponent;
        this.javaProjectDebugger = javaProjectDebugger;
        this.focusAction = focusAction;
        this.stepCenter = stepCenter;
    }

    public void initialListener(){
        stepCenter.addStepOverListener(new StepOverListener() {
            @Override
            public void onStepOver(int lineNumber, String fullClassName, @Nullable String fileName) {

            }
        });
    }

    public void initialUI(){
        initialListener();
        Button debugProjectButton = javaFxComponent.get("debugProjectButton", Button.class);
        //debug 开始按钮
        debugProjectButton.setOnMouseClicked(e->{
           javaProjectDebugger.startDebug();
           focusAction.debugWindowsFocus();
        });
        //debug 下一步按钮
        Button stepOverButton=javaFxComponent.get("stepOverButton",Button.class);
        stepOverButton.setOnMouseClicked(e-> javaProjectDebugger.stepOver());
        //恢复运行按钮
        Button resumeProgramButton=javaFxComponent.get("resumeProgramButton",Button.class);
        resumeProgramButton.setOnMouseClicked(e-> javaProjectDebugger.resume());
    }
}
