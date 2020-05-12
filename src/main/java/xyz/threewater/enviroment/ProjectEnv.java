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

    @Value("${project.name}")
    private String projectName;

    private boolean isMavenProject;

    private boolean isGitProject;



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
        return getProjectPath()+"/out";
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

    public String getGitCmd() {
        return getGit()+" -C \""+getProjectPath()+"\" log --pretty=format:\"%an%x09%ad%x09%s\" --date=format:%c";
    }

    public boolean isMavenProject() {
        return isMavenProject;
    }

    public void setMavenProject(boolean mavenProject) {
        isMavenProject = mavenProject;
    }

    public boolean isGitProject() {
        return isGitProject;
    }

    public void setGitProject(boolean gitProject) {
        isGitProject = gitProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
