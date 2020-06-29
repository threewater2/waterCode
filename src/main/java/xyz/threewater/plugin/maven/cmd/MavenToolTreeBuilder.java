package xyz.threewater.plugin.maven.cmd;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.event.FileSaver;
import xyz.threewater.exception.CommandExcuteException;

import java.io.IOException;

@Component
public class MavenToolTreeBuilder {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private FileSaver fileSaver;

    private MavenCmdExecutor executor;

    public MavenToolTreeBuilder(FileSaver fileSaver,MavenCmdExecutor mavenCmdExecutor) {
        this.executor=mavenCmdExecutor;
        this.fileSaver = fileSaver;
    }

    @Value("classpath:/images/bar/setting-black.svg")
    private Resource settingIcon;

    private Node showResultNode;
    private TabPane bottomTabPane;

    public TreeItem<Node> build(Node showResultNode, TabPane bottomTabPane){
        this.showResultNode=showResultNode;
        this.bottomTabPane=bottomTabPane;
        TreeItem<Node> root=new TreeItem<>(new Label("LifeCycle"));
        for(MavenCmd mavenCmd:MavenCmd.values()){
            ImageView icon=getSettingIcon();
            Label label=new Label(mavenCmd.toString());
            HBox cmdButton=new HBox(icon,label);
            TreeItem<Node> item=new TreeItem<>(cmdButton);
            root.getChildren().add(item);
            initEvent(cmdButton,mavenCmd);
        }
        return root;
    }

    public ImageView getSettingIcon(){
        try {
            return new ImageView(new Image(settingIcon.getInputStream(),
                    16,16,false,true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 每当执行maven命令的时候保存源代码
     */
    public void initEvent(HBox HBox, MavenCmd mavenCmd){
        HBox.setOnMouseClicked(e->{
            //saveFile
            logger.debug("maven cmd start execute:{}",mavenCmd.toString());
            fileSaver.saveFile();
            try {
                CommandResult commandResult = executor.execute(mavenCmd,true);
                showResult(commandResult);
                logger.debug("maven cmd execute finished,content:{}",commandResult.getOutPut());
            } catch (CommandExcuteException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * 聚焦于输出结果面板
     * @param commandResult 输出结果
     */
    private void showResult(CommandResult commandResult){
        TextArea textArea=(TextArea) showResultNode;
        textArea.setText(commandResult.getOutPut());
        SingleSelectionModel<Tab> selectionModel = bottomTabPane.getSelectionModel();
        selectionModel.select(1);
    }
}
