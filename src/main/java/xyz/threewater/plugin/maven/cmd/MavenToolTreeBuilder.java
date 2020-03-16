package xyz.threewater.plugin.maven.cmd;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;

import java.io.IOException;

@Component
public class MavenToolTreeBuilder {

    @Value("classpath:/images/bar/setting-black.svg")
    private Resource settingIcon;

    private Node showResultNode;

    public TreeItem<Node> build(Node showResultNode){
        this.showResultNode=showResultNode;
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


    public void initEvent(HBox HBox, MavenCmd mavenCmd){
        HBox.setOnMouseClicked(e->{
            try {
                CommandResult commandResult = mavenCmd.execute(true);
                showResult(commandResult);
            } catch (CommandExcuteException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void showResult(CommandResult commandResult){
        TextArea textArea=(TextArea) showResultNode;
        textArea.setText(commandResult.getOutPut());
    }
}
