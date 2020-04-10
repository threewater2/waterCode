package xyz.threewater.run;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import xyz.threewater.search.FileSearch;
import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SourceFile {

    private final FileSearch fileSearch;

    private final ProjectStatus projectStatus;

    @Value("${project.path}")
    private String projectPath;

    @Value("classpath:project/source-file-list.txt")
    private Resource sourceListFile;

    private List<String> sourceFile=new ArrayList<>();

    public SourceFile(FileSearch fileSearch, ProjectStatus projectStatus) {
        this.fileSearch = fileSearch;
        this.projectStatus = projectStatus;
    }

    public String getSourceFileString(){
        if(projectStatus.isChangeBeforeLastBuild()){
            sourceFile=fileSearch.RegexSearch(projectPath,".+\\.java","test");
            return Strings.join(sourceFile, ' ');
        }
        return "";
    }

    public File storeToFile(){
        String sourceArg=getSourceFileString();
        try {
            FileUtil.saveFile(sourceArg,sourceListFile.getFile());
            return sourceListFile.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
