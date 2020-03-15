package xyz.threewater.style;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CSSHolder {

    private String[] cssFiles={
            "xyz/threewater/waterCode.css",
            "xyz/threewater/editor/java-keywords.css"
    };


    public List<String> getCss(){
        List<String> res=new ArrayList<>();
        Arrays.stream(cssFiles).forEach(cssFile->res.add(new ClassPathResource(cssFile).getPath()));
        return res;
    }
}
