package xyz.threewater.plugin.maven.praser;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import xyz.threewater.resources.ImageResources;
import xyz.threewater.utils.PathUtil;


public class MavenTreePane extends TreeView<Node> {
    public MavenTreePane(){
        setStyle("-fx-border-width: 1;-fx-border-color: blue");
    }



    public TreeItem<Node> getDependencyTree(String path) throws XmlParseException {
        TreeNode<String> rootNode = MavenXmlParser.getInstance().parse(path);
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

    public Node getDependencyNode(String dependencyDes){
        String imgPath= PathUtil.getResourceFromClassPath("images/maven/maven-dependency-black.png");
        ImageView imageView = ImageResources.getInstance().getImageView(imgPath,true);
        return new HBox(imageView,new Label(dependencyDes));
    }


}
