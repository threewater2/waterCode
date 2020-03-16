package xyz.threewater.console;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TabNameGenerator;
import com.kodedu.terminalfx.config.TerminalConfig;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TerminalInitializer {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    TerminalBuilder terminalBuilder = new TerminalBuilder();

    public TerminalInitializer(){
    }

    public void initialize(AnchorPane terminalAnchorPane,
                           TabPane terminalTabPane,
                           Button addTerminalButton){
        configTerminal();
        initialNameGenerator();
        initialAnchorPosition(terminalTabPane,addTerminalButton);
        initialResizeEvent(terminalAnchorPane,terminalTabPane);
        initialAddEvent(addTerminalButton,terminalTabPane);
        TerminalTab terminal = terminalBuilder.newTerminal();
        terminalTabPane.getTabs().add(terminal);
    }
    public void initialNameGenerator(){
        terminalBuilder.setNameGenerator(new TabNameGenerator() {
            String name="Console ";
            int count=1;
            @Override
            public String next() {
                return name+count++;
            }
        });
    }

    public void configTerminal(){
        TerminalConfig terminalConfig=new TerminalConfig();
        terminalConfig.setFontSize(16);
        terminalConfig.setCursorBlink(true);
        terminalConfig.setCtrlCCopy(true);
        terminalConfig.setCtrlVPaste(true);
        terminalConfig.setEnableClipboardNotice(false);
        terminalConfig.setScrollbarVisible(true);
        logger.debug("terminal font family:{}",terminalConfig.getFontFamily());
        terminalBuilder.setTerminalConfig(terminalConfig);
    }

    private void initialAnchorPosition(TabPane tabPane, Button button){
        AnchorPane.setTopAnchor(tabPane,0d);
        AnchorPane.setLeftAnchor(tabPane,0d);
        AnchorPane.setTopAnchor(button,0d);
        AnchorPane.setRightAnchor(button,2d);
    }

    private void initialResizeEvent(AnchorPane anchorPane,TabPane tabPane){
        anchorPane.prefHeightProperty().bind(tabPane.prefHeightProperty());
        anchorPane.prefWidthProperty().bind(tabPane.prefWidthProperty());
    }

    public void initialAddEvent(Button addTerminalButton, TabPane terminalTabPane){
        addTerminalButton.setOnMouseClicked(e->
                terminalTabPane.getTabs().addAll(terminalBuilder.newTerminal()));
    }
}
