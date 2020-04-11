package xyz.threewater.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.search.FileSearch;

import java.io.File;
public abstract class JavaProject implements Project {

    private Logger logger= LoggerFactory.getLogger(JavaProject.class);

    @Value("${javac}")
    private String javac;

    @Value("${java}")
    private String java;

    @Value("${project.compilePath}")
    protected String outPutDir;

    private ClassPath classPath;

    private final SourceFile sourceFile;

    private final ProjectStatus projectStatus;

    private final CommandExecutor commandExecutor;


    public JavaProject(SourceFile sourceFile, ProjectStatus projectStatus,
                       ClassPath classPath,CommandExecutor commandExecutor) {
        this.sourceFile = sourceFile;
        this.projectStatus = projectStatus;
        this.commandExecutor = commandExecutor;
        this.classPath=classPath;
    }

    @Override
    public void build() {
        String sourFileStr=sourceFile.storeToFile().getAbsolutePath();
        String classPathStr=classPath.storeClassPath().getAbsolutePath();
        createOutDir();
        String command = String.join(" ",
                javac,"-encoding","utf-8", "-d", outPutDir,
                "-classpath","@"+classPathStr,"@"+sourFileStr);
        copyResource();
        logger.debug("copy resource finished");
        logger.debug("command :{}",command);
        projectStatus.setChangeBeforeLastBuild(false);
        try {
            CommandResult commandResult = commandExecutor.executeCmd(command);
            logger.debug("command execute finished code:{},result{}",commandResult.getExitCode(),commandResult.getOutPut());
        } catch (CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }

    private void createOutDir(){
        File file=new File(outPutDir);
        if(!file.exists()){
            file.mkdir();
        }
    }

    @Override
    public void run(String className) {
        if(projectStatus.isChangeBeforeLastBuild()){
            build();
        }
        String command=String.join(" ",
                java,"-classpath",classPath+";"+outPutDir,className);
        try {
            commandExecutor.executeCmd(command);
        } catch (CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void copyResource();
}
