package xyz.threewater.build;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandLineWindow;
import xyz.threewater.enviroment.ClassPath;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.exception.BuildFailedException;
import xyz.threewater.exception.CommandExcuteException;
import xyz.threewater.search.FileSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 可以用来build当前项目到输出目录
 */
@Component
public class JavaProjectBuilder{

    ProjectEnv projectEnv;
    CommandLineWindow commandLineWindow;
    private final Logger logger= LoggerFactory.getLogger(JavaProjectBuilder.class);
    private final FileSearch fileSearch;
    private final SourceFile sourceFile;
    private final ClassPath classPath;


    public JavaProjectBuilder(ProjectEnv projectEnv, CommandLineWindow commandLineWindow,
                              FileSearch fileSearch, SourceFile sourceFile,
                              ClassPath classPath) {
        this.projectEnv = projectEnv;
        this.commandLineWindow=commandLineWindow;
        this.fileSearch = fileSearch;
        this.sourceFile = sourceFile;
        this.classPath = classPath;
    }

    /**
     * 对单个java文件进行编译
     * @param sourceFile 源文件路径
     * @param outPutPath 指定生成的目录 如果为空则默认和源代码文件目录相同
     */
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

    public void buildProject() {
        String absolutePath=sourceFile.getSourceFile().getAbsolutePath();
        String classPathFilePath=classPath.buildClasspathFile().getAbsolutePath();
        clearOutDir();
        //没有classpath就不要-classpath参数
        String classPath="";
        String newClassPath="";
        try {
            newClassPath = FileUtils.readFileToString(new File(classPathFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!newClassPath.isEmpty()){
            classPath="-classpath "+"@"+classPathFilePath;
        }
        String command = String.join(" ",
                projectEnv.getJavac(),"-encoding","utf-8", "-d", projectEnv.getOutPutPath(),
                classPath,"@"+absolutePath);
        logger.debug("copy resource finished");
        logger.debug("command :{}",command);
        copyResource();
        try {
            //执行命令。同时绑定输出到输出面板
            commandLineWindow.executeCmd(command);
        } catch (CommandExcuteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 复制资源文件
     */
    private void copyResource() {
        if(projectEnv.isMavenProject()){
            copyMavenResource();
            return;
        }
        //普通java项目的复制
        List<String> normalFile = fileSearch.fileSearch(projectEnv.getProjectPath(), new JavaFileFilter());
        normalFile.forEach(filePath->{
            filePath=filePath.replace("\\","/");
            String desPath=projectEnv.getOutPutPath()+"/"+filePath.substring(projectEnv.getProjectPath().length()+1);
            try {
                logger.info("normal file copy :from {}, to {}",filePath,desPath);
                FileUtils.copyFile(new File(filePath),new File(desPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * 复制maven的资源文件
     */
    private void copyMavenResource(){
        String searchPath=projectEnv.getProjectPath()+"/src/main";
        List<String> filePath = this.fileSearch.fileSearch(searchPath,new JavaFileFilter());
        logger.info("maven resource :{}",filePath);
        filePath.forEach(fileStr->{
            fileStr=fileStr.replace("\\","/");
            String innerResource=projectEnv.getProjectPath()+"/src/main/java/";
            String norMalResource=projectEnv.getProjectPath()+"/src/main/resource/";
            String desPath;
            if(fileStr.startsWith(innerResource)){
                desPath=projectEnv.getOutPutPath()+"/"+fileStr.substring(innerResource.length()+1);
            }else {
                desPath=projectEnv.getOutPutPath()+"/"+fileStr.substring(norMalResource.length()+1);
            }
            try {
                logger.debug("maven file copy :from {}, to {}",fileStr,desPath);
                FileUtils.copyFile(new File(fileStr),new File(desPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 过滤掉所有的java文件
     */
    static class JavaFileFilter implements FileSearch.ValidFile{
        @Override
        public boolean isValidFile(File file) {
            if(file.isDirectory()) return true;
            else {
                return !file.getName().endsWith(".java");
            }
        }
    }

    /**
     * 清空输出目录
     */
    private void clearOutDir(){
        File file=new File(projectEnv.getOutPutPath());
        try {
            FileUtils.deleteDirectory(file);
            boolean mkdir = file.mkdir();
            if(!mkdir) throw new RuntimeException("cannot mk dir");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
