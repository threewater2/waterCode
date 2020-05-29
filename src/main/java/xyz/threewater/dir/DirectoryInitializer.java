package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.ProjectEnv;

@Component
public class DirectoryInitializer {

    private final DirectoryModel directoryModel;

    private final ProjectEnv projectEnv;

    public DirectoryInitializer(DirectoryModel directoryModel, ProjectEnv projectEnv) {
        this.directoryModel = directoryModel;
        this.projectEnv = projectEnv;
    }

    public TreeItem<Node> getTreeItem(){
        return directoryModel.getTreeItem(projectEnv.getProjectPath());
    }
}
