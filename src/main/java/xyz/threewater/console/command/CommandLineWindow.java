package xyz.threewater.console.command;

import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.action.FocusAction;
import xyz.threewater.enviroment.JavaFxComponent;
import xyz.threewater.exception.CommandExcuteException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用来执行一个windows命令，并且获取其实时输出，显示到JavaFx控件上
 */
@Component
public class CommandLineWindow {

    private final JavaFxComponent javaFxComponent;
    private final FocusAction focusAction;
    private final Logger logger= LoggerFactory.getLogger(CommandLineWindow.class);

    public CommandLineWindow(JavaFxComponent javaFxComponent, FocusAction focusAction) {
        this.javaFxComponent = javaFxComponent;
        this.focusAction = focusAction;
    }

    public void executeCmd(String cmd) throws CommandExcuteException {
        TextArea outPutTextArea = javaFxComponent.get("outPutTextArea", TextArea.class);
        outPutTextArea.clear();

        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            InputStream errorStream = ps.getErrorStream();
            BufferedReader errorOut=new BufferedReader(new InputStreamReader(errorStream));
            //聚焦于输出面板
            focusAction.outPutWindowsFocus();
            String line;
            StringBuilder forDebug= new StringBuilder();
            while ((line = br.readLine()) != null) {
                outPutTextArea.appendText(line+"\n");
                forDebug.append(line);
            }
            logger.debug("normal result:{}",forDebug);
            //输出不为零表示有错误发生
            if(ps.exitValue()!=0){
                outPutTextArea.appendText("ERROR!------------------\n");
                forDebug.setLength(0);
                while ((line = errorOut.readLine()) != null) {
                    outPutTextArea.appendText(line+"\n");
                    forDebug.append(line);
                }
                logger.debug("error result:{}",forDebug);
            }
            br.close();
            ps.waitFor();
        } catch (Exception e) {
            throw new CommandExcuteException(e);
        }
    }
}
