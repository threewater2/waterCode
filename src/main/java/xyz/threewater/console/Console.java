package xyz.threewater.console;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import javafx.scene.control.TabPane;
import xyz.threewater.function.Resizeable;

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
        TerminalBuilder builder=new TerminalBuilder();
        TerminalTab terminalTab = builder.newTerminal();
        this.getTabs().add(terminalTab);
    }
}
