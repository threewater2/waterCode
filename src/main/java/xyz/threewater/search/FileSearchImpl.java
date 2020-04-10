package xyz.threewater.search;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileSearchImpl implements FileSearch {

    private List<String> filePath=new ArrayList<>();
    private Pattern fileNamePattern;
    private String ignoreDir;

    public FileSearchImpl() {
    }

    @Override
    public List<String> RegexSearch(String basePath, String fileNameRegex,String ignoreDir) {
        fileNamePattern =Pattern.compile(fileNameRegex);
        this.ignoreDir=ignoreDir;
        File root=new File(basePath);
        filePath.clear();
        fileSearch(root);
        return filePath;
    }


    private void fileSearch(File file){
        if(file.isFile()){
            Matcher matcher = fileNamePattern.matcher(file.getName());
            if(matcher.matches()){
                filePath.add(file.getAbsolutePath());
            }
        }else {
            for(File child: Objects.requireNonNull(file.listFiles())){
                if(child.getName().equals(ignoreDir)) continue;
                fileSearch(child);
            }
        }
    }

    @Override
    public List<String> keywordSearch(String basePath, String keyword) {
        String regex=".*"+keyword+".*";
        fileNamePattern =Pattern.compile(regex);
        filePath.clear();
        fileSearch(new File(basePath));
        return filePath;
    }
}
