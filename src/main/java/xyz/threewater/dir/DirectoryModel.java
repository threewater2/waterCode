package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.*;
import xyz.threewater.editor.JavaKeyWordEditor;
import xyz.threewater.utils.FileUtil;

import java.io.File;
public class DirectoryModel {

    private TabPane tabPane;

    public DirectoryModel(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public TreeItem<Node> getTreeItem(String path){
        File rootDir=new File(path);
        return initDirectoryTree(rootDir, null);
    }

    @SuppressWarnings("unchecked")
    private TreeItem<Node> initDirectoryTree(File dir, TreeItem<Node> root){
        String fileName=dir.getName();
        FileLabel fileLabel=new FileLabel(dir,fileName);
        TreeItem<Node> treeItem=new TreeItem<>(fileLabel);
        if(root==null){
            root=treeItem;
        }else {
            root.getChildren().addAll(treeItem);
        }
        //不是文件夹
        if(!dir.isDirectory()) {
            //添加右侧导航树的点击事件
            addLabelClickEvent(fileLabel);
            return root;
        }
        File[] files = dir.listFiles();
        //空文件夹
        if(files==null) return root;
        for (File file: files) {
            initDirectoryTree(file,treeItem);
        }
        return root;
    }

    private void addLabelClickEvent(FileLabel fileLabel){
        fileLabel.setOnMouseClicked(mouseEvent -> {
            String name = fileLabel.getFile().getName();
            String fileContent= FileUtil.file2String(fileLabel.getFile());
            Tab tab=new Tab(name,new JavaKeyWordEditor(fileContent,fileLabel.getFile().getPath()));
            tabPane.getTabs().addAll(tab);
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(tab);
        });
    }
}
