package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DirectoryInitializer {

    private DirectoryModel directoryModel;

    @Value("${project.path}")
    private String projectPath;

    public DirectoryInitializer(DirectoryModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    public TreeItem<Node> getTreeItem(TabPane tabPane){
        return directoryModel.getTreeItem(projectPath,tabPane);
    }
}
