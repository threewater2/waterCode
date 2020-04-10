package xyz.threewater.search;

import java.util.List;

public interface FileSearch {
    List<String> RegexSearch(String basePath,String fileNameRegex,String ignoreDir);

    List<String> keywordSearch(String basePath,String keyword);
}
