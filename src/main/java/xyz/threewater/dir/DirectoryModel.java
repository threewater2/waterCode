package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.threewater.controller.WaterCodeController;
import xyz.threewater.editor.FileEditor;
import xyz.threewater.editor.JavaKeyWordEditor;
import xyz.threewater.event.FileSaver;
import xyz.threewater.utils.FileUtil;

import java.io.File;
@Component
public class DirectoryModel {

    private TabPane editorTabPane;

    private FileEditor fileEditor;

    public DirectoryModel(FileEditor fileEditor) {
        this.fileEditor = fileEditor;
    }

    public TreeItem<Node> getTreeItem(String path, TabPane tabPane){
        this.editorTabPane=tabPane;
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

    /**
     * 读取磁盘中的文件，放到内存中,自动关闭原来的流,
     * 当tab页面关闭时，保存文件
     */
    private void addLabelClickEvent(FileLabel fileLabel){
        fileLabel.setOnMouseClicked(mouseEvent -> {
            String name = fileLabel.getFile().getName();
            Tab tab=new Tab(name,fileEditor.openFile(fileLabel.getFile()));
            tab.setOnCloseRequest(e-> fileEditor.closeFile(fileLabel.getFile()));
            editorTabPane.getTabs().addAll(tab);
            SingleSelectionModel<Tab> selectionModel = editorTabPane.getSelectionModel();
            selectionModel.select(tab);
        });
    }


}
