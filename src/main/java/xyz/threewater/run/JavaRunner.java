package xyz.threewater.run;

import org.springframework.stereotype.Component;
import xyz.threewater.action.FocusAction;
import xyz.threewater.console.command.CommandLineWindow;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.CommandExcuteException;

@Component
public class JavaRunner implements Runner{

    CommandLineWindow commandLineWindow;

    private final FocusAction focusAction;

    ProjectEnv projectEnv;

    public JavaRunner(CommandLineWindow commandLineWindow, ProjectEnv projectEnv, FocusAction focusAction) {
        this.commandLineWindow = commandLineWindow;
        this.projectEnv = projectEnv;
        this.focusAction = focusAction;
    }

    @Override
    public void runClass(String classPath,String fullClassName) throws CommandExcuteException {
        String cmd = String.join(" ", projectEnv.getJava(), "-cp", classPath, fullClassName);
        commandLineWindow.executeCmd(cmd);
        focusAction.outPutWindowsFocus();
    }
}
