package xyz.threewater.run;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.utils.FileUtil;

import java.io.*;

@Component("MavenProjectClassPath")
public class MavenProjectClassPath extends ClassPath {

    private Logger logger= LoggerFactory.getLogger(MavenProjectClassPath.class);

    @Value("classpath:project/maven-class-path.txt")
    private Resource mavenClassPathFile;

    private final CommandExecutor commandExecutor;

    @Value("${maven.cmd}")
    private String mvn;

    @Value("${project.path}")
    private String projectPath;

    public MavenProjectClassPath(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }


    @Override
    public String getClassPath() {
//            String command= String.join(" ",
//                    mvn,"dependency:tree",
//                    "-DoutputFile=" + mavenClassPathFile.getFile().getAbsolutePath(),
//                    "-f",projectPath);
        File file = storeClassPath();
        return FileUtil.file2String(file);
    }

    @Override
    public File storeClassPath() {
        try {
            String command = String.join(" ",
                    mvn,"dependency:build-classpath",
                    "-Dmdep.outputFile="+mavenClassPathFile.getFile().getAbsolutePath(),
                    "-f",projectPath);
            logger.debug("maven classPath command:{}",command);
            CommandResult commandResult = commandExecutor.executeCmd(command);
            logger.debug("maven classPath execute code {},result:{}",commandResult.getExitCode(),commandResult.getOutPut());
            String classPath = FileUtil.file2String(mavenClassPathFile.getFile());
            classPath = classPath.replace("\\", "/");
            classPath = classPath.replace("\n", " ");
            classPath="\""+classPath+"\"";
            FileUtil.saveFile(classPath,mavenClassPathFile.getFile());
            return mavenClassPathFile.getFile();
        } catch (IOException | CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }
}
