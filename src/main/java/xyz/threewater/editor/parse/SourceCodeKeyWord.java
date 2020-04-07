package xyz.threewater.editor.parse;

import org.springframework.stereotype.Component;
import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SourceCodeKeyWord {
    public Set<String> getKeyWords(String sourceCode){
        sourceCode = sourceCode.replaceAll("[,;.()<>{}=:!\n&|]", " ");
        String[] split = sourceCode.split(" +");
        List<String> strings = Arrays.asList(split);
        return new HashSet<>(strings);
    }

    public Set<String> getKeyWords(File file){
        return getKeyWords(FileUtil.file2String(file));
    }
}
