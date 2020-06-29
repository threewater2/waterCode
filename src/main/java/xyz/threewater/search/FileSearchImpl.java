package xyz.threewater.search;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FileSearchImpl implements FileSearch {

    @Override
    public List<String> RegexSearch(String basePath,String regex) {
        List<String> filePath=new ArrayList<>();
        File root=new File(basePath);
        search(root,file -> file.getName().matches(regex),filePath);
        return filePath;
    }

    @Override
    public List<String> fileSearch(File file,ValidFile validFile){
        List<String> filePath=new ArrayList<>();
        search(file,validFile,filePath);
        return filePath;
    }

    @Override
    public List<String> fileSearch(String basePath,ValidFile validFile){
        return fileSearch(new File(basePath),validFile);
    }

    @Override
    public List<String> keywordSearch(String basePath, String keyword) {
        List<String> filePath=new ArrayList<>();
        search(new File(basePath), file -> {
            if(file.isDirectory()) return false;
            else return file.getName().contains(keyword);
        },filePath);
        return filePath;
    }

    private void search(File file,ValidFile validFile,List<String> filePath){
        if(!validFile.isValidFile(file)) return;
        if(file.isFile()){
            filePath.add(file.getAbsolutePath());
        }else {
            File[] files = file.listFiles();
            if(files==null) return;
            for(File child: files){
                search(child,validFile,filePath);
            }
        }
    }
}
