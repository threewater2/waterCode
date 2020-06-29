package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.editor.FileEditor;
import xyz.threewater.enviroment.JavaFxComponent;

import java.io.File;

@Component
public class DirectoryModel {

    //private static final Logger logger= LoggerFactory.getLogger(DirectoryModel.class);
    private final FileLabelEvent fileLabelEvent;

    public DirectoryModel(FileLabelEvent fileLabelEvent) {
        this.fileLabelEvent=fileLabelEvent;
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
        //为目录和文件添加通用的点击事件
        fileLabelEvent.addLabelClickEvent(fileLabel,treeItem);
        if(root==null){
            root=treeItem;
        }else {
            root.getChildren().addAll(treeItem);
        }
        //不是文件夹
        if(!dir.isDirectory()) {
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
}
