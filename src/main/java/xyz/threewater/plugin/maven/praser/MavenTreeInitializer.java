package xyz.threewater.plugin.maven.praser;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.exception.MavenTreeCmdExeception;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MavenTreeInitializer {

    private final CommandExecutor commandExecutor;
    private final ProjectEnv projectEnv;

    public MavenTreeInitializer(CommandExecutor commandExecutor, ProjectEnv projectEnv, JavaFxComponent javaFxComponent) {
        this.commandExecutor = commandExecutor;
        this.projectEnv = projectEnv;
    }

    public TreeItem<javafx.scene.Node> build(){
        Node mavenNode = getMavenNode();
        TreeItem<javafx.scene.Node> dependencies=new TreeItem<>(new Label("dependencies"));
        resolveTreeNode(mavenNode,dependencies);
        return dependencies;
    }

    private void resolveTreeNode(Node node, TreeItem<javafx.scene.Node> root){
        TreeItem<javafx.scene.Node> child=new TreeItem<>(new Label(getDependencyStr(node)));
        root.getChildren().add(child);
        for (Node childNode:node.getChildNodes()){
            resolveTreeNode(childNode,child);
        }
    }

    private String getDependencyStr(Node node){
        String groupId = node.getGroupId();
        String artifactId = node.getArtifactId();
        String version = node.getVersion();
        String scope = node.getScope();
        return Stream.of(groupId, artifactId, version, scope)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(":"));
    }

    private Node getMavenNode(){
        String cmd=projectEnv.getMaven()+" dependency:tree -DoutputFile=./mavenTree.txt -DoutputType=text";
        try {
            commandExecutor.executeCmd(cmd);
            InputType type = InputType.TEXT;
            Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./mavenTree.txt")), StandardCharsets.UTF_8));
            Parser parser = type.newParser();
            return parser.parse(r);
        } catch (CommandExcuteException | FileNotFoundException | ParseException e) {
            throw new MavenTreeCmdExeception("执行"+cmd+"失败",e);
        }
    }
}
