package xyz.threewater.plugin.maven.cmd;

public enum  MavenCmd {
    CLEAN("clean"),
    PACKAGE("package"),
    INSTALL("install"),
    DEPLOY("deploy"),
    TEST("test");

    MavenCmd(String cmd){
        this.cmd=cmd;
    }

    private String cmd;

    @Override
    public String toString() {
        return cmd;
    }
}
