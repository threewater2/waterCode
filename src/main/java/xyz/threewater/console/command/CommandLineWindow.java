package xyz.threewater.console.command;

import javafx.scene.control.TextArea;
import org.springframework.stereotype.Component;
import xyz.threewater.exception.CommandExcuteException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 用来执行一个windows命令，并且获取其实时输出，显示到JavaFx控件上
 */
@Component
public class CommandLineWindow {
    private TextArea textArea;

    public void makeCommandLineWindow(TextArea textArea){
        this.textArea=textArea;
    }

    public void executeCmd(String cmd) throws CommandExcuteException {
        textArea.clear();
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                textArea.appendText(line+"\n");
            }
            br.close();
            ps.waitFor();
        } catch (Exception e) {
            throw new CommandExcuteException(e);
        }
    }
}
