package xyz.threewater.plugin.maven.praser;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.resources.ImageResources;
import xyz.threewater.utils.PathUtil;

import java.io.IOException;

@Component
class MavenTreeBuilder {

    @Value("classpath:images/bar/chart-black.svg")
    private Resource mavenIcon;

    private MavenXmlParser parser;

    public MavenTreeBuilder(MavenXmlParser parser){
        this.parser=parser;
    }

    public TreeItem<Node> getDependencyTree(String rootPomPath) throws XmlParseException {
        TreeNode<String> rootNode = parser.parse(new TreeNode<>(),rootPomPath);
        TreeItem<Node> rootItem=new TreeItem<>(new Label("dependency"));
        resolveTreeNode(rootItem,rootNode);
        return rootItem;
    }


    private void resolveTreeNode(TreeItem<Node> treeItem,TreeNode<String> treeNode){
        for(TreeNode<String> child:treeNode){
            String dependencyDes = child.getValue();
            TreeItem<Node> childrenItem=new TreeItem<>(getDependencyNode(dependencyDes));
            treeItem.getChildren().add(childrenItem);
            resolveTreeNode(childrenItem,child);
        }
    }

    private Node getDependencyNode(String dependencyDes){
        try {
            ImageView imageView=new ImageView(new Image(mavenIcon.getInputStream()));
            return new HBox(imageView,new Label(dependencyDes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
