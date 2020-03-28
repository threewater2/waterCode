package xyz.threewater.plugin.maven.praser;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.plugin.maven.cmd.MavenToolTreeBuilder;

@Component
public class MavenTreeInitializer {

    @Value("${project.path}")
    private String projectPath;

    @Value("${project.name}")
    private String projectName;

    private MavenTreeBuilder treeBuilder;
    private MavenToolTreeBuilder toolTreeBuilder;

    public MavenTreeInitializer(MavenTreeBuilder treeBuilder,
                                MavenToolTreeBuilder toolTreeBuilder){
        this.treeBuilder =treeBuilder;
        this.toolTreeBuilder=toolTreeBuilder;
    }

    @SuppressWarnings("unchecked")
    public void initialize(TreeView<Node> treeView, Node showResultPane, TabPane bottomTabPane){
        try {
            TreeItem<Node> dependencyTree = treeBuilder.getDependencyTree(getProjectPath());
            TreeItem<Node> toolTree = toolTreeBuilder.build(showResultPane,bottomTabPane);
            TreeItem<Node> root=new TreeItem<>(new Label(projectName));
            root.getChildren().addAll(toolTree,dependencyTree);
            treeView.setRoot(root);
        } catch (XmlParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProjectPath(){
        return projectPath+"/pom.xml";
    }
}
