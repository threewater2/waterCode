package xyz.threewater.plugin.git;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.CommandExcuteException;
import java.util.Arrays;

/**
 * 用来初始化git提交历史，如果不是一个项目就不初始化
 */
@Component
public class GitLogInitializer {

    CommandExecutor executor=new CommandExecutor();

    private final ProjectEnv projectEnv;

    private final JavaFxComponent javaFxComponent;

    public GitLogInitializer(ProjectEnv projectEnv, JavaFxComponent javaFxComponent) {
        this.projectEnv = projectEnv;
        this.javaFxComponent = javaFxComponent;
    }

    public void initial() {
        Tab gitTab = javaFxComponent.get("gitTab", Tab.class);
        CommandResult result;
        try {
            result = executor.executeCmd(projectEnv.getGitCmd());
        } catch (CommandExcuteException e) {
            throw new RuntimeException(e);
        }
        gitTab.setContent(getNode(result));
    }


    private Node getNode(CommandResult result){
        String outPut = result.getOutPut();
        //先判断是不是git项目
        if(!isGitProject(outPut)){
            projectEnv.setGitProject(false);
            return new Text("not a git project");
        }
        String[] rows = outPut.split("\n");
        ListView<String> listView=new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Arrays.stream(rows).forEach(str-> listView.getItems().addAll(str));
        return listView;
    }

    /**
     * 判断是不是一个Git项目
     */
    private boolean isGitProject(String result){
        return !result.startsWith("fatal: not a git repository (or any of the parent directories): .git");
    }
}
