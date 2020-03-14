package xyz.threewater.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import xyz.threewater.bar.TitleBar;
import xyz.threewater.common.component.ImageTextButton;
import xyz.threewater.console.Console;
import xyz.threewater.dir.NavTree;
import xyz.threewater.plugin.maven.MavenPlugin;

import javax.swing.text.html.ImageView;
import java.util.function.Consumer;

@Component
public class IndexController {
    public Pane bottomContent;
    public Label buildResult;
    public HBox bottomToolBar;
    @FXML
    private TitleBar titleBar;
    @FXML
    private NavTree navTree;
    @FXML
    private MavenPlugin mavenPlugin;
    @FXML
    private Console console;
    @FXML
    private TabPane tabPane;
    @FXML
    private void initialize() {
        titleBar.initTitleBar();
        navTree.initTree(tabPane);
        bottomToolBar.getChildren().forEach(node -> ((ImageTextButton)node).createImageTextButton());
    }
}
