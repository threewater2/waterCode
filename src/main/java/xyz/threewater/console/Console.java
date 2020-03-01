package xyz.threewater.console;

import javafx.embed.swing.SwingNode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import xyz.threewater.function.Resizeable;

import javax.swing.*;

public class Console extends TabPane implements Resizeable {

    public Console(){
        setPrefHeight(200);
        setStyle("-fx-border-width: 1;-fx-border-color: red");
        openTerminalTab();
        initEvent();
    }

    public void initEvent(){
        resizeableHeight(this,5);
    }

    private void openTerminalTab(){
        SwingNode swingNode=new SwingNode();
        this.getTabs().add(new Tab("terminal",swingNode));
        JediTerminal jediTerminal = new JediTerminal();
        SwingUtilities.invokeLater(() -> swingNode.setContent(jediTerminal.getSwingComponent()));
    }
}
