package xyz.threewater.enviroment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectEnv {

    @Value("${javac}")
    private String javac;

    @Value("${java}")
    private String java;

    @Value("${maven.cmd}")
    private String maven;

    @Value("${git.path}")
    private String git;

    @Value("${project.path}")
    private String projectPath;

    @Value("${project.compilePath}")
    private String outPutPath;

    public String getJavac() {
        return javac;
    }

    public void setJavac(String javac) {
        this.javac = javac;
    }

    public String getMaven() {
        return maven;
    }

    public void setMaven(String maven) {
        this.maven = maven;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getOutPutPath() {
        return outPutPath;
    }

    public void setOutPutPath(String outPutPath) {
        this.outPutPath = outPutPath;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }
}
