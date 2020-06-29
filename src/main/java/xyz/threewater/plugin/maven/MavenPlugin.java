package xyz.threewater.plugin.maven;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.plugin.maven.cmd.MavenToolTreeBuilder;
import xyz.threewater.plugin.maven.praser.MavenTreeInitializer;

import java.io.File;

@Component
public class MavenPlugin {

    private final MavenTreeInitializer mavenTreeInitializer;
    private final MavenToolTreeBuilder mavenToolTreeBuilder;
    private final JavaFxComponent javaFxComponent;
    private final ProjectEnv projectEnv;

    public MavenPlugin(MavenTreeInitializer mavenTreeInitializer, MavenToolTreeBuilder mavenToolTreeBuilder, JavaFxComponent javaFxComponent, ProjectEnv projectEnv) {
        this.mavenTreeInitializer = mavenTreeInitializer;
        this.mavenToolTreeBuilder = mavenToolTreeBuilder;
        this.javaFxComponent = javaFxComponent;
        this.projectEnv = projectEnv;
    }

    @SuppressWarnings("unchecked")
    public void initial(){
        TreeView<Node> mavenTree = javaFxComponent.get("mavenTree", TreeView.class);
        if(!isMavenProject()){
            mavenTree.setRoot(new TreeItem<>(new Label("not a maven project")));
            return;
        }
        TreeItem<Node> lifeCycle = mavenToolTreeBuilder.build();
        TreeItem<Node> dependency = mavenTreeInitializer.build();
        TreeItem<Node> root=new TreeItem<>(new Label(projectEnv.getProjectName()));
        root.getChildren().add(lifeCycle);
        root.getChildren().add(dependency);
        mavenTree.setRoot(root);
    }

    /**
     * 判断是不是maven项目
     */
    private boolean isMavenProject(){
        File file=new File(projectEnv.getProjectPath());
        return file.exists();
    }
}
