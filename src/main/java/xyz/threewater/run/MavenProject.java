package xyz.threewater.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.search.FileSearch;

@Component("MavenProject")
public class MavenProject extends JavaProject {

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
        fileSearch.RegexSearch(projectPath,".*" +
                                           "",null);
    }
}
