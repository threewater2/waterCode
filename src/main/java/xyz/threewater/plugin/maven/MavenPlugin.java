package xyz.threewater.plugin.maven;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import xyz.threewater.function.Resizeable;
import xyz.threewater.plugin.maven.cmd.MavenToolBar;
import xyz.threewater.plugin.maven.praser.MavenTreePane;
import xyz.threewater.plugin.maven.praser.XmlParseException;

public class MavenPlugin extends MavenTreePane implements Resizeable {
    public void initTree(){
        String path="C:\\Users\\water\\IdeaProjects\\waterIde\\pom.xml";
        TreeItem<Node> treeItem = new TreeItem<>(new Label("waterCode"));
        try {
            treeItem.getChildren().add(new MavenToolBar().getToolBar());
            treeItem.getChildren().add(getDependencyTree(path));
        } catch (XmlParseException e) {
            e.printStackTrace();
        }
        setRoot(treeItem);
    }


    public MavenPlugin(){
        initTree();
        initEvent();
    }

    private void initEvent(){
        resizeableWidth(this,10);
    }
}
