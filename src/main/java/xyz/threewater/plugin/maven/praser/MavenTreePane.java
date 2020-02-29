package xyz.threewater.plugin.maven.praser;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MavenTreePane extends TreeView<Node> {
    public MavenTreePane(){
        setStyle("-fx-border-width: 1;-fx-border-color: blue");
        initTree();
    }



    public void initTree(){
        String path="C:\\Users\\water\\IdeaProjects\\waterIde\\pom.xml";
        TreeItem<Node> treeItem = null;
        try {
            treeItem = getTreeItem(path);
        } catch (XmlParseException e) {
            e.printStackTrace();
        }
        setRoot(treeItem);
    }


    private TreeItem<Node> getTreeItem(String path) throws XmlParseException {
        TreeNode<String> rootNode = MavenXmlParser.parse(path);
        TreeItem<Node> rootItem=new TreeItem<>(new Label("waterIde"));
        resolveTreeNode(rootItem,rootNode);
        return rootItem;
    }

    private void resolveTreeNode(TreeItem<Node> treeItem,TreeNode<String> treeNode){
        for(TreeNode<String> child:treeNode){
            String dependencyDes = child.getValue();
            TreeItem<Node> childrenItem=new TreeItem<>(new Label(dependencyDes));
            treeItem.getChildren().add(childrenItem);
            resolveTreeNode(childrenItem,child);
        }
    }
}
