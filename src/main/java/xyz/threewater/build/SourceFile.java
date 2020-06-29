package xyz.threewater.build;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.enviroment.ProjectEnv;
import xyz.threewater.run.ProjectStatus;
import xyz.threewater.search.FileSearch;
import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 当前项目源码文件持有类
 * 它可以解析项目目录，并且把所有源码文件的绝对路径存入txt文件中
 * 方便后续编译
 */
@Component
public class SourceFile {

    private final FileSearch fileSearch;

    private final ProjectStatus projectStatus;

    private final ProjectEnv projectEnv;

    @Value("classpath:project/source-file-list.txt")
    private Resource sourceListFile;

    public SourceFile(FileSearch fileSearch, ProjectStatus projectStatus, ProjectEnv projectEnv) {
        this.fileSearch = fileSearch;
        this.projectStatus = projectStatus;
        this.projectEnv = projectEnv;
    }

    /**
     * 搜索当前项目下的所有源码文件
     * 获取其路径，并且以空格分开
     */
    private String getSourceFileString(){
        if(projectStatus.isChangeBeforeLastBuild()){
            List<String> sourceFile = fileSearch.fileSearch(projectEnv.getProjectPath(), file -> {
                if (file.isDirectory()) return !file.getName().contains("test");
                else return file.getName().matches(".+\\.java");
            });
            return Strings.join(sourceFile, ' ');
        }
        return "";
    }

    /**
     * 把源码文件存入文件中，因为命令行有长度限制
     */
    public File getSourceFile(){
        String sourceArg=getSourceFileString();
        try {
            FileUtil.saveFile(sourceArg,sourceListFile.getFile());
            return sourceListFile.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
