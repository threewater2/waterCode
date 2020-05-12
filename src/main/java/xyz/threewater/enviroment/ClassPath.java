package xyz.threewater.enviroment;

import org.apache.logging.log4j.util.Strings;
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
import java.util.HashSet;
import java.util.Set;

/**
 * 用来保存classpath
 */
@Component
public class ClassPath {

    Set<String> normalClassPathSet =new HashSet<>();

    private final Logger logger = LoggerFactory.getLogger(ClassPath.class);

    //用来保存maven的classpath
    @Value("classpath:project/maven-class-path.txt")
    private Resource classPathFile;

    private final CommandExecutor commandExecutor;

    private final ProjectEnv projectEnv;

    private boolean isEmpty=false;

    public ClassPath(CommandExecutor commandExecutor, ProjectEnv projectEnv) {
        this.commandExecutor = commandExecutor;
        this.projectEnv = projectEnv;
    }

    public void addClassPath(String path){
       normalClassPathSet.add(path);
    }

    public File buildClasspathFile(){
        String normalClassPath = Strings.join(normalClassPathSet, ';');
        String mavenClassPath="";
        //如果时maven项目，还需要获取maven的jar包
        if(projectEnv.isMavenProject()){
            mavenClassPath = getMavenClassPath();
        }
        String allClasspath="";
        if("".equals(normalClassPath)){
            //都为空
            if("".equals(mavenClassPath)){
                setEmpty(true);
            }else {
                //maven有classpath
                allClasspath=mavenClassPath;
            }
        }else {
            //普通有classpath.maven没有
            if("".equals(mavenClassPath)){
                allClasspath=normalClassPath;
            }else {
                //不同有classpath，maven也有
                allClasspath=normalClassPath+";"+mavenClassPath;
            }
        }
        try {
            FileUtil.saveFile(allClasspath,classPathFile.getFile());
            return classPathFile.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    /**
     * 通过maven命令解析maven的classpath
     * 并且存入文件中
     */
    private String getMavenClassPath(){
        try {
            String command = String.join(" ",
                    projectEnv.getMaven(),"dependency:build-classpath",
                    "-Dmdep.outputFile="+ classPathFile.getFile().getAbsolutePath(),
                    "-f",projectEnv.getProjectPath());
            logger.debug("maven classPath command:{}",command);
            CommandResult commandResult = commandExecutor.executeCmd(command);
            logger.debug("maven classPath execute code {},result:{}",commandResult.getExitCode(),commandResult.getOutPut());
            String mavenClassPath = FileUtil.file2String(classPathFile.getFile());
            mavenClassPath = mavenClassPath.replace("\\", "/");
            mavenClassPath = mavenClassPath.replace("\n", " ");
            mavenClassPath="\""+mavenClassPath+"\"";
            return mavenClassPath;
        } catch (IOException | CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }
}
