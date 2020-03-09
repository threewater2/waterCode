package xyz.threewater.plugin.maven.cmd;

import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.enviroment.ProjectEnviroMent;
import xyz.threewater.exception.CommandExcuteException;

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

    private static CommandExecutor commandExecutor=new CommandExecutor();

    public CommandResult execute(boolean skipTest) throws CommandExcuteException {
        CommandResult commandResult= commandExecutor.executeCmd(getCmdWithArg(skipTest));
        System.out.println(commandResult.getExitCode());
        System.out.println(commandResult.getOutPut());
        return commandResult;
    }

    private String getCmdWithArg(boolean skipTest){
        String projectPath=ProjectEnviroMent.getProjectPath();
        String mvnPath=ProjectEnviroMent.getMvnPath();
        if(skipTest) {
            return mvnPath+cmd+" -DskipTests"+" -f "+projectPath;
        }
        return mvnPath+cmd+projectPath;
    }


    @Override
    public String toString() {
        return cmd;
    }
}
