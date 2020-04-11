package xyz.threewater.run;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.search.FileSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component("MavenProject")
public class MavenProject extends JavaProject {

    private Logger logger= LoggerFactory.getLogger(MavenProject.class);

    private FileSearch fileSearch;

    @Value("${project.path}")
    private String projectPath;

    public MavenProject(SourceFile sourceFile, ProjectStatus projectStatus,
                        @Qualifier("MavenProjectClassPath") ClassPath classPath,
                        CommandExecutor commandExecutor, FileSearch fileSearch) {
        super(sourceFile, projectStatus, classPath,commandExecutor);
        this.fileSearch = fileSearch;
    }

    @Override
    public void copyResource() {
        String searchPath=projectPath+"/src/main";
        List<String> filePath = this.fileSearch.fileSearch(searchPath, file -> {
            if(file.isDirectory()) return true;
            else {
                return !file.getName().endsWith(".java");
            }
        });
        logger.debug("maven resource :{}",filePath);
        filePath.forEach(fileStr->{
            fileStr=fileStr.replace("\\","/");
            String innerResource=projectPath+"/src/main/java/";
            String norMalResource=projectPath+"/src/main/resource/";
            String desPath;
            if(fileStr.startsWith(innerResource)){
                desPath=outPutDir+"/"+fileStr.substring(innerResource.length()+1);
            }else {
                desPath=outPutDir+"/"+fileStr.substring(norMalResource.length()+1);
            }
            try {
                FileUtils.copyFile(new File(fileStr),new File(desPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
