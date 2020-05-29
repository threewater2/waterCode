package xyz.threewater.enviroment;

import javafx.scene.control.Tab;
import org.springframework.stereotype.Component;
import xyz.threewater.dir.FileLabel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 记录所有用户打开的文件,以及对应的tab
 */
@Component
public class OpenFileStatus {
    private final Map<FileLabel, Tab> openFileMap=new HashMap<>();

    public void setOpenFileTab(FileLabel fileLabel,Tab tab){
        openFileMap.put(fileLabel,tab);
    }

    public void closeOpenFileTab(FileLabel fileLabel){
        openFileMap.remove(fileLabel);
    }

    public Optional<Tab> getOpenFileTab(FileLabel fileLabel){
        return Optional.ofNullable(openFileMap.get(fileLabel));
    }
}
