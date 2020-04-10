package xyz.threewater.run;

import java.io.File;

public class NormalProjectClassPath extends ClassPath {
    @Override
    public String getClassPath() {
        return String.join(";",getClassPathList());
    }

    @Override
    public File storeClassPath() {
        return null;
    }
}
