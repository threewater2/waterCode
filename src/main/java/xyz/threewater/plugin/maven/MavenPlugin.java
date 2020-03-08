package xyz.threewater.plugin.maven;

import xyz.threewater.function.Resizeable;
import xyz.threewater.plugin.maven.praser.MavenTreePane;

public class MavenPlugin extends MavenTreePane implements Resizeable {
    public MavenPlugin(){
        initEvent();
    }

    private void initEvent(){
        resizeableWidth(this,10);
    }
}
