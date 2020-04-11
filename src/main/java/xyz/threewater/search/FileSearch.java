package xyz.threewater.search;

import java.io.File;
import java.util.List;

public interface FileSearch {
    List<String> RegexSearch(String basePath,String regex);

    List<String> keywordSearch(String basePath,String keyword);

    List<String> fileSearch(File file,ValidFile validFile);

    List<String> fileSearch(String basePath,ValidFile validFile);

    @FunctionalInterface
    interface ValidFile{
        boolean isValidFile(File file);
    }
}
