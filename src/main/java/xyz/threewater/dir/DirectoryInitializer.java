package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;

@Component
public class DirectoryInitializer {

    private final DirectoryModel directoryModel;

    private final ProjectEnv projectEnv;

    private final JavaFxComponent javaFxComponent;

    public DirectoryInitializer(DirectoryModel directoryModel, ProjectEnv projectEnv, JavaFxComponent javaFxComponent) {
        this.directoryModel = directoryModel;
        this.projectEnv = projectEnv;
        this.javaFxComponent = javaFxComponent;
    }

    @SuppressWarnings("unchecked")
    public void initial(){
        TreeItem<Node> treeItem = directoryModel.getTreeItem(projectEnv.getProjectPath());
        TreeView<Node> dirTree = javaFxComponent.get("dirTree", TreeView.class);
        dirTree.setRoot(treeItem);
    }
}
