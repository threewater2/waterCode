package xyz.threewater.event;

import javafx.scene.control.TabPane;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileSaver {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    Map<File,CodeArea> fileMap=new HashMap<>();

    public void saveFile(){
        logger.debug("opened file save");
        for (File file:fileMap.keySet()){
            String content=fileMap.get(file).getText();
            try {
                FileUtil.saveFile(content,file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addFile(File file,CodeArea codeArea){
        fileMap.put(file,codeArea);
    }

    public void removeFile(File file){
        fileMap.remove(file);
    }
}
