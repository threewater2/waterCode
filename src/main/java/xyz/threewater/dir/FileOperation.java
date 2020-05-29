package xyz.threewater.dir;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.JavaFxComponent;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * 文件添加，删除，操作类，包括对虚拟节点的添加
 */
@Component
public class FileOperation {

    private final JavaFxComponent javaFxComponent;

    public FileOperation(JavaFxComponent javaFxComponent) {
        this.javaFxComponent = javaFxComponent;
    }


    /**
     * 添加文件，同时在虚拟节点中添加
     * @param parent 父目录，或文件
     * @param newFileName 新的文件名称
     * @return 添加成功返回true，失败返回false
     */
    public Optional<FileLabel> addFile(File parent, String newFileName){
        File newFile;
        if(parent.isDirectory()){
            newFile=new File(parent.getAbsolutePath()+File.separator+newFileName);
        }else {
            newFile=new File(parent.getParent()+File.separator+newFileName);
        }
        if(newFile.exists()) return Optional.empty();
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileLabel fileLabel = new FileLabel(newFile, newFileName);
        return Optional.of(fileLabel);
    }

    /**
     * 创建文件夹
     * @param file 父文件夹或者文件
     * @param dirName 新文件夹名称
     * @return 添加成功返回true，添加失败返回false
     */
    public Optional<FileLabel> addDirectory(File file, String dirName){
        if(file.isFile()){
            file=file.getParentFile();
        }
        File newDir=new File(file.getAbsolutePath()+File.separator+dirName);
        if(newDir.mkdir()){
            FileLabel fileLabel = new FileLabel(newDir, dirName);
            return Optional.of(fileLabel);
        }else {
            return Optional.empty();
        }
    }


    /**
     * 删除当前文件，同时node节点也删除
     * @param file 实体文件节点
     * @param node  虚拟文件节点
     */
    public boolean deleteFile(File file,TreeItem<Node> node){
        boolean deleted = file.delete();
        if(deleted){
            TreeItem<Node> parent = node.getParent();
            parent.getChildren().remove(node);
            return true;
        }
        return false;
    }

    /**
     * 重命名一个文件，虚拟目录也进行重命名
     * @param file 旧文件
     * @param newFileName 新文件名称
     * @param node 虚拟节点
     * @return 命名成功返回true，失败返回false
     */
    public boolean renameFile(File file,String newFileName,TreeItem<Node> node){
        File newFile=new File(file.getParent()+File.separator+newFileName);
        if(file.renameTo(newFile)){
            FileLabel value = (FileLabel)node.getValue();
            value.setText(newFileName);
            return true;
        }else {
            return false;
        }
    }
}
