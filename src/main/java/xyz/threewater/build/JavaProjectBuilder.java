package xyz.threewater.build;

import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandLineWindow;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.BuildFailedException;
import xyz.threewater.exception.CommandExcuteException;

@Component
public class JavaProjectBuilder implements ProjectBuilder {

    ProjectEnv projectEnv;
    CommandLineWindow commandLineWindow;


    public JavaProjectBuilder(ProjectEnv projectEnv,CommandLineWindow commandLineWindow) {
        this.projectEnv = projectEnv;
        this.commandLineWindow=commandLineWindow;
    }

    @Override
    public String buildSourceFile(String sourceFile, String outPutPath) throws BuildFailedException {
        try{
            if(outPutPath==null||outPutPath.equals("")){
                commandLineWindow.executeCmd(projectEnv.getJavac()+" "+sourceFile);
            }else {
                commandLineWindow.executeCmd(projectEnv.getJavac()+" "+sourceFile+" -d "+outPutPath);
            }
        }catch (CommandExcuteException e) {
            throw new BuildFailedException(e);
        }
        return null;
    }

    @Override
    public String buildProject(String projectRootPath, String outPutPath) {
        return null;
    }
}
