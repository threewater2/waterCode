package xyz.threewater.enviroment;

public class ProjectEnviroMent {
    private static String projectPath="C:\\Users\\water\\IdeaProjects\\waterIde";

    private static String mvnPath="C:/Users/water/app/apache-maven-3.6.3/bin/mvn.cmd ";

    public static String getProjectPath() {
        return projectPath;
    }

    public static void setProjectPath(String projectPath) {
        ProjectEnviroMent.projectPath = projectPath;
    }

    public static String getMvnPath() {
        return mvnPath;
    }

    public static void setMvnPath(String mvnPath) {
        ProjectEnviroMent.mvnPath = mvnPath;
    }
}
