package xyz.threewater.editor;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.threewater.build.JavaProjectBuilder;
import xyz.threewater.build.ProjectBuilder;
import xyz.threewater.build.SourceCodeAnalysis;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.BuildFailedException;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.run.JavaRunner;
import xyz.threewater.run.Runner;

import java.io.File;

@Component
public class RightClickMenu {

    JavaFxComponent javaFxComponent;
    private JavaEditor javaEditor;
    private final ProjectBuilder builder;
    private final Runner runner;
    private final ProjectEnv projectEnv;

    public RightClickMenu(JavaFxComponent javaFxComponent, ProjectBuilder builder, Runner runner, ProjectEnv projectEnv) {
        this.javaFxComponent = javaFxComponent;
        this.builder = builder;
        this.runner = runner;
        this.projectEnv = projectEnv;
    }

    public void bind(JavaEditor javaEditor){
        this.javaEditor=javaEditor;
        ListView<Label> rightClickMenu = javaFxComponent.get("rightClickMenu",ListView.class);
        rightClickMenu.setOnMouseClicked(mouseEvent->{
            if(mouseEvent.getButton()== MouseButton.PRIMARY){
                Label selectedItem = rightClickMenu.getSelectionModel().getSelectedItem();
                if(selectedItem.getText().equals("run")){
                    run();
                }
            }
        });
    }

    public void run() {
        String sourceFilePath=javaEditor.getFilePath();
        SourceCodeAnalysis codeAnalysis=new SourceCodeAnalysis(new File(sourceFilePath));
        String fullClassName = codeAnalysis.getFullClassName();
        try {
            builder.buildSourceFile(sourceFilePath,projectEnv.getOutPutPath());
            runner.runClass(projectEnv.getOutPutPath(),fullClassName);
        } catch (BuildFailedException | CommandExcuteException e) {
            e.printStackTrace();
        }
    }
}
