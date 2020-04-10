package xyz.threewater.run;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;
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
            CommandResult commandResult = commandExecutor.executeCmd(command);
            String classPath = FileUtil.file2String(mavenClassPathFile.getFile());
            classPath = classPath.replace("\\", "/");
            classPath = classPath.replace("\n", " ");
            classPath="\""+classPath+"\"";
            FileUtil.saveFile(classPath,mavenClassPathFile.getFile());
            System.out.println(commandResult.getOutPut());
            return mavenClassPathFile.getFile();
        } catch (IOException | CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }
}
