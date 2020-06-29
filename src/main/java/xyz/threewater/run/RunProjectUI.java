package xyz.threewater.run;

import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import xyz.threewater.action.RunAndBuildAction;
import xyz.threewater.build.JavaProjectBuilder;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.MainClassList;

import java.util.Optional;

@Component
public class RunProjectUI {
    private final JavaFxComponent javaFxComponent;
    private final MainClassList mainClassList;
    private final JavaProjectBuilder javaProjectBuilder;
    private final JavaProjectRunner javaProjectRunner;
    private final RunAndBuildAction runAndBuildAction;

    public RunProjectUI(JavaFxComponent javaFxComponent, MainClassList mainClassList, JavaProjectBuilder javaProjectBuilder, JavaProjectRunner javaProjectRunner, RunAndBuildAction runAndBuildAction) {
        this.javaFxComponent = javaFxComponent;
        this.mainClassList = mainClassList;
        this.javaProjectBuilder = javaProjectBuilder;
        this.javaProjectRunner = javaProjectRunner;
        this.runAndBuildAction = runAndBuildAction;
    }

    public void initial(){
        Button runProjectButton = javaFxComponent.get("runProjectButton", Button.class);
        runProjectButton.setOnMouseClicked(e->{
            Optional<String> currentRunnableClass = mainClassList.getCurrentRunnableClass();
            currentRunnableClass.ifPresent(runAndBuildAction::runAndBuildProject);
        });
    }
}
