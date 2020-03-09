package xyz.threewater.plugin.maven.cmd;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.resources.ImageResources;
import xyz.threewater.utils.PathUtil;

import java.util.function.Predicate;

public class MavenToolBar {
    ImageResources imageResources=ImageResources.getInstance();
    public TreeItem<Node> getToolBar(){
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
        String imagePath= PathUtil.getResourceFromClassPath("images/maven/setting-black.png");
        return imageResources.getImageView(imagePath,true);
    }


    public void initEvent(HBox HBox,MavenCmd mavenCmd){
        HBox.setOnMouseClicked(e->{
            try {
                CommandResult execute = mavenCmd.execute(true);
                showResultPane(HBox.getScene(),execute);
            } catch (CommandExcuteException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void showResultPane(Scene sence, CommandResult commandResult){
        StackPane bottomContent = (StackPane)sence.lookup("#bottomContent");
        Label buildResult=(Label)sence.lookup("#buildResult");
        buildResult.setVisible(true);
        buildResult.setText(commandResult.getOutPut());
        for(Node node:bottomContent.getChildren()){
            if(node==buildResult) continue;
            node.setVisible(false);
        }
    }

    public static void main(String[] args) {

    }
}
