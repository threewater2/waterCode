package xyz.threewater.debug;

import com.sun.jdi.*;
import com.sun.jdi.event.LocatableEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.action.*;
import xyz.threewater.enviroment.JavaFxComponent;

import javax.annotation.Nullable;
import java.util.List;

@Component
public class JavaProjectDebuggerUI {
    private final Logger logger= LoggerFactory.getLogger(JavaProjectDebuggerUI.class);
    private final JavaFxComponent javaFxComponent;
    private final JavaProjectDebugger javaProjectDebugger;
    private final FocusAction focusAction;
    private final DebugStatus debugStatus;
    private final VMCenter vmCenter;
    private final StepCenter stepCenter;

    public JavaProjectDebuggerUI(JavaFxComponent javaFxComponent, JavaProjectDebugger javaProjectDebugger, FocusAction focusAction, DebugStatus debugStatus, VMCenter vmCenter, StepCenter stepCenter) {
        this.javaFxComponent = javaFxComponent;
        this.javaProjectDebugger = javaProjectDebugger;
        this.focusAction = focusAction;
        this.debugStatus = debugStatus;
        this.vmCenter = vmCenter;
        this.stepCenter = stepCenter;
    }

    public void initialListener(){
        stepCenter.addStepOverListener(new StepOverListener() {
            @Override
            public void onStepOver(int lineNumber, String fullClassName, @Nullable String fileName) {

            }
        });
    }

    @SuppressWarnings("unchecked")
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
        resumeProgramButton.setOnMouseClicked(e-> {
            javaProjectDebugger.resume();
            CodePosition codePosition = debugStatus.getCodePosition();
            vmCenter.resume(codePosition);
        });
        ListView<String> debugVarListView=javaFxComponent.get("debugVarListView",ListView.class);
        //每当断点停止的时候，或者step event停止的时候，应该展示局部变量
        vmCenter.addVariablesChangedListener(variables -> {
            debugVarListView.getItems().clear();
            debugVarListView.getItems().addAll(variables);
        });
    }
}
