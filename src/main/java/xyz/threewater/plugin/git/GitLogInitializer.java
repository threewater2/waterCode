package xyz.threewater.plugin.git;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;
import java.util.Arrays;

@Component
public class GitLogInitializer {

    CommandExecutor executor=new CommandExecutor();

    @Value("${git.log.cmd}")
    private String cmd;

    public void initial(Tab gitTab) {
        CommandResult result;
        try {
            result = executor.executeCmd(cmd);
        } catch (CommandExcuteException e) {
            throw new RuntimeException(e);
        }
        gitTab.setContent(getNode(result));
    }


    private Node getNode(CommandResult result){
        String[] rows = result.getOutPut().split("\n");
        ListView<String> listView=new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Arrays.stream(rows).forEach(str-> listView.getItems().addAll(str));
        return listView;
    }
}
