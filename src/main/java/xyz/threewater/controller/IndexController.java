package xyz.threewater.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;
import xyz.threewater.bar.TitleBar;
import xyz.threewater.console.Console;
import xyz.threewater.dir.NavTree;
import xyz.threewater.plugin.maven.MavenPlugin;

@Getter
@Setter
public class IndexController {
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
    }
}
