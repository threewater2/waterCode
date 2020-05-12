package xyz.threewater.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.action.FocusAction;
import xyz.threewater.console.command.CommandLineWindow;
import xyz.threewater.enviroment.ClassPath;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.CommandExcuteException;

@Component
public class JavaProjectRunner {

    CommandLineWindow commandLineWindow;

    private final FocusAction focusAction;
    private final ClassPath classPath;
    private final ProjectEnv projectEnv;
    private final Logger logger= LoggerFactory.getLogger(JavaProjectRunner.class);

    public JavaProjectRunner(CommandLineWindow commandLineWindow, ProjectEnv projectEnv, FocusAction focusAction, ClassPath classPath) {
        this.commandLineWindow = commandLineWindow;
        this.projectEnv = projectEnv;
        this.focusAction = focusAction;
        this.classPath = classPath;
    }

    /**
     * 运行单个java类
     */
    public void runClass(String classPath,String fullClassName) throws CommandExcuteException {
        String cmd = String.join(" ", projectEnv.getJava(), "-cp", classPath, fullClassName);
        commandLineWindow.executeCmd(cmd);
        focusAction.outPutWindowsFocus();
    }

    /**
     * 运行当前项目
     * @param fullClassName 全限定类名
     */
    public void runProject(String fullClassName){
        String classPathFilePath = classPath.buildClasspathFile().getAbsolutePath();
        String classPath="";
        if(!classPath.isEmpty()){
            classPath="-classpath @"+ classPathFilePath +";"+projectEnv.getOutPutPath();
        }else {
            classPath="-classpath "+projectEnv.getOutPutPath();
        }
        String command=String.join(" ",
                projectEnv.getJava(),
                "-Dfile.encoding=utf-8",
                classPath,fullClassName);
        logger.debug("run cmd is:{}",command);
        try {
            commandLineWindow.executeCmd(command);
        } catch (CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }
}
