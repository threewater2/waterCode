package xyz.threewater.search;

import org.junit.jupiter.api.Test;

import java.util.List;

public class FileSearchTest {
    @Test
    public void test(){
        FileSearch fileSearch=new FileSearchImpl();
        String path="C:/Users/water/IdeaProjects/waterCode";
        List<String> list = fileSearch.fileSearch(path, file -> {
            if(file.isDirectory()) return !file.getName().contains("test");
            else return file.getName().matches(".+\\.java");
        });
        list.forEach(System.out::println);
    }
}
