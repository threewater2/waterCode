package xyz.threewater.dir;

import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import com.sun.javafx.scene.control.skin.TabPaneSkin;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.editor.FileEditor;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.enviroment.OpenFileStatus;
import xyz.threewater.utils.JavaFxUtil;

import java.util.Optional;

/**
 * 针对右侧目录树的点击事件
 */
@Component
public class FileLabelEvent {

    private static final Logger logger= LoggerFactory.getLogger(FileLabelEvent.class);
    private final FileEditor fileEditor;
    private final JavaFxComponent javaFxComponent;
    private final FileOperation fileOperation;
    private final OpenFileStatus openFileStatus;
    private VBox dirMenu;
    private final Label label=new Label();
    private final TextField textField=new TextField();

    public FileLabelEvent(FileEditor fileEditor, JavaFxComponent javaFxComponent, FileOperation fileOperation, OpenFileStatus openFileStatus) {
        this.fileEditor = fileEditor;
        this.javaFxComponent = javaFxComponent;
        this.fileOperation = fileOperation;
        this.openFileStatus = openFileStatus;
    }

    /**
     * 读取磁盘中的文件，放到内存中,自动关闭原来的流,
     * 当tab页面关闭时，保存文件
     */
    public void addLabelClickEvent(FileLabel fileLabel,TreeItem<Node> root){
        TabPane editorTabPane = javaFxComponent.get("editorTabPane", TabPane.class);
        fileLabel.setOnMouseClicked(mouseEvent -> {
            //左键打开,只有文件才能打开
            if(mouseEvent.getButton()== MouseButton.PRIMARY&&fileLabel.getFile().isFile()){
                String name = fileLabel.getFile().getName();
                Tab tab=new Tab(name,fileEditor.openFile(fileLabel.getFile()));
                openFileStatus.setOpenFileTab(fileLabel,tab);
                tab.setOnCloseRequest(e-> {
                    fileEditor.closeAndSaveFile(fileLabel.getFile());
                    openFileStatus.closeOpenFileTab(fileLabel);
                });
                editorTabPane.getTabs().addAll(tab);
                SingleSelectionModel<Tab> selectionModel = editorTabPane.getSelectionModel();
                selectionModel.select(tab);
            //右键菜单
            }else if (mouseEvent.getButton()== MouseButton.SECONDARY){
                openRightClickMenu(mouseEvent);
                addRightClickMenuEvent(fileLabel,root);
            }
            //防止事件传递到父组件
            mouseEvent.consume();
        });
    }

    private void addRightClickMenuEvent(FileLabel fileLabel, TreeItem<Node> parent){
        Label addFile = javaFxComponent.get("addFile", Label.class);
        Label addDir = javaFxComponent.get("addDir", Label.class);
        Label deleteFile = javaFxComponent.get("deleteFile", Label.class);
        Label renameFile = javaFxComponent.get("renameFile", Label.class);
        //添加文件
        addFile.setOnMouseClicked(mouseEvent->{
            dirMenu.setVisible(false);
            Optional<ButtonType> buttonType = openDialog("enter file name:", "file name");
            if(buttonType.isPresent()){
                String text = textField.getText();
                Optional<FileLabel> fileLabelValue = fileOperation.addFile(fileLabel.getFile(), text);
                //为新生成的节点添加点击事件
                fileLabelValue.ifPresent(newFileLabel -> addFileLabelNode(fileLabel, parent, newFileLabel));
                logger.debug("enter file name:{}",text);
            }
        });
        //添加目录
        addDir.setOnMouseClicked(mouseEvent->{
            dirMenu.setVisible(false);
            Optional<ButtonType> buttonType = openDialog("enter directory name:", "directory name");
            if(buttonType.isPresent()){
                String text=textField.getText();
                Optional<FileLabel> fileLabelValue = fileOperation.addDirectory(fileLabel.getFile(), text);
                //为新生成的节点添加点击事件
                fileLabelValue.ifPresent(newFileLabel -> addFileLabelNode(fileLabel, parent, newFileLabel));
                logger.debug("enter directory name:{}",text);

            }
        });
        //删除文件
        deleteFile.setOnMouseClicked(mouseEvent->{
            dirMenu.setVisible(false);
            boolean deleted = fileOperation.deleteFile(fileLabel.getFile(), parent);
            if(deleted){
                //如果有已经打开的标签页应该关闭
                closeTabPane(fileLabel);
            }
        });
        //重命名文件
        renameFile.setOnMouseClicked(mouseEvent->{
            dirMenu.setVisible(false);
            Optional<ButtonType> buttonType = openDialog("rename file", "new file name");
            if(buttonType.isPresent()){
                String text=textField.getText();
                fileOperation.renameFile(fileLabel.getFile(), text, parent);
            }
        });
    }

    /**
     * 添加虚拟节点
     * @param fileLabel 旧的节点
     * @param parent 旧的节点
     * @param newFileLabel 新的节点
     */
    private void addFileLabelNode(FileLabel fileLabel, TreeItem<Node> parent, FileLabel newFileLabel) {
        TreeItem<Node> newTreeItem = new TreeItem<>(newFileLabel);
        if(fileLabel.getFile().isFile()){
            parent.getParent().getChildren().add(newTreeItem);
        }else {
            parent.getChildren().add(newTreeItem);
        }
        addLabelClickEvent(newFileLabel,newTreeItem);
    }

    private void openRightClickMenu(MouseEvent mouseEvent){
        dirMenu = javaFxComponent.get("dirMenu", VBox.class);
        JavaFxUtil.moveToMousePosition(mouseEvent,dirMenu);
        dirMenu.setVisible(true);
    }

    private Optional<ButtonType> openDialog(String title,String prop){
        dirMenu.setVisible(false);
        label.setText(title);
        textField.setText(prop);
        VBox vBox=new VBox(label,textField);
        Dialog<ButtonType> dialog = JavaFxUtil.showDialog(vBox);
        return dialog.showAndWait();
    }

    private void closeTabPane(FileLabel fileLabel){
        TabPane editorTabPane = javaFxComponent.get("editorTabPane", TabPane.class);
        Optional<Tab> openFileTab = openFileStatus.getOpenFileTab(fileLabel);
        openFileTab.ifPresent(tab -> {
            TabPaneSkin tabPaneSkin=(TabPaneSkin) editorTabPane.getSkin();
            TabPaneBehavior behavior = tabPaneSkin.getBehavior();
            if(behavior.canCloseTab(tab)){
                behavior.closeTab(tab);
            }
        });
    }

}
