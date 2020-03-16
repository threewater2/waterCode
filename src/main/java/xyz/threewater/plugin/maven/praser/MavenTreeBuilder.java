package xyz.threewater.plugin.maven.praser;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class MavenTreeBuilder {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

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
            ImageView imageView=new ImageView(new Image(mavenIcon.getInputStream(),
                    16,16,false,true));
            logger.debug("dependency description:{}",dependencyDes);
            return new HBox(imageView,new Label(dependencyDes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
