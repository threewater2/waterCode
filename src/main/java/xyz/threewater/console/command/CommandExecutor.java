package xyz.threewater.console.command;

import org.springframework.stereotype.Component;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.utils.StreamUtil;

import java.io.IOException;

@Component
public class CommandExecutor {
    public CommandResult executeCmd(String cmd) throws CommandExcuteException {
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            exec.waitFor();
            String resultContent= StreamUtil.getStr(exec.getInputStream());
            int exitValue=exec.exitValue();
            return new CommandResult(resultContent, exitValue);
        } catch (IOException | InterruptedException e) {
            throw new CommandExcuteException("命令执行异常");
        }
    }
}
