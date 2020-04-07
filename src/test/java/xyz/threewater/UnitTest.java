package xyz.threewater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.threewater.editor.parse.SourceCodeKeyWord;
import xyz.threewater.editor.parse.Trie;

import java.io.File;
import java.util.*;

public class UnitTest {
    @Test
    public void test1(){
        String path="C:\\Users\\water\\IdeaProjects\\waterCode\\src\\main\\java\\xyz\\threewater\\editor\\parse\\Trie.java";
        SourceCodeKeyWord word=new SourceCodeKeyWord();
        Set<String> keyWords = word.getKeyWords(new File(path));
        List<String> except= Arrays.asList("java","Trie","class","Node","import","String");
        for(String s:except){
            Assertions.assertTrue(keyWords.contains(s));
        }

        Trie<String> trie=new Trie<>();
        keyWords.forEach(keyWord->trie.add(keyWord,null));
        String prefix="No";
        List<String> strings = trie.startWith(prefix);
        for(String s:strings){
            System.out.println(s);
        }
        Assertions.assertTrue(strings.contains("Node"));
    }

    @Test
    public void  test3(){
        List<String> list= Arrays.asList("package","public","private");
        Trie<String> trie=new Trie<>();
        String prefix="pr";
        list.forEach(str->trie.add(str,null));
        List<String> strings = trie.startWith(prefix);
        for(String s:strings){
            System.out.println(s);
        }
        Assertions.assertTrue(strings.contains("private"));
    }

    @Test
    public void  test4(){
        List<String> list= Collections.singletonList("Java");
        Trie<String> trie=new Trie<>();
        String prefix="Ja";
        list.forEach(str->trie.add(str,null));
        List<String> strings = trie.startWith(prefix);
        list.forEach(str->trie.add(str,null));
        for(String s:strings){
            System.out.println(s);
        }
        Assertions.assertTrue(strings.contains("Java"));
    }
}
