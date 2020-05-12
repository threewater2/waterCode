package xyz.threewater.plugin.maven.praser;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.plugin.maven.cmd.MavenToolTreeBuilder;

import java.io.File;

@Component
public class MavenTreeInitializer {

    private final ProjectEnv projectEnv;

    private MavenTreeBuilder treeBuilder;
    private MavenToolTreeBuilder toolTreeBuilder;

    public MavenTreeInitializer(ProjectEnv projectEnv, MavenTreeBuilder treeBuilder,
                                MavenToolTreeBuilder toolTreeBuilder){
        this.projectEnv = projectEnv;
        this.treeBuilder =treeBuilder;
        this.toolTreeBuilder=toolTreeBuilder;
    }

    @SuppressWarnings("unchecked")
    public void initialize(TreeView<Node> treeView, Node showResultPane, TabPane bottomTabPane){
        //不是一个maven项目
        if(!isMavenProject()){
            TreeItem<Node> root=new TreeItem<>(new Label("not a maven project"));
            treeView.setRoot(root);
            return;
        }
        try {
            TreeItem<Node> dependencyTree = treeBuilder.getDependencyTree(getProjectPath());
            //maven命令树
            TreeItem<Node> toolTree = toolTreeBuilder.build(showResultPane,bottomTabPane);
            //maven依赖树
            TreeItem<Node> root=new TreeItem<>(new Label(projectEnv.getProjectName()));
            root.getChildren().addAll(toolTree,dependencyTree);
            treeView.setRoot(root);
        } catch (XmlParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProjectPath(){
        return projectEnv.getProjectPath()+"/pom.xml";
    }


    /**
     * 判断是不是maven项目
     */
    private boolean isMavenProject(){
        File file=new File(getProjectPath());
        return file.exists();
    }
}
