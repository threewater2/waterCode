package xyz.threewater.plugin.maven.cmd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;

@Component
public class MavenCmdExecutor {

    private CommandExecutor commandExecutor;

    @Value("${project.path}")
    private String projectPath;

    @Value("${maven.cmd}")
    private String mvnPath;

    public MavenCmdExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public CommandResult execute(MavenCmd mavenCmd,boolean skipTest) throws CommandExcuteException {
        CommandResult commandResult= commandExecutor.executeCmd(getCmdWithArg(mavenCmd,skipTest));
        System.out.println(commandResult.getExitCode());
        System.out.println(commandResult.getOutPut());
        return commandResult;
    }

    private String getCmdWithArg(MavenCmd mavenCmd,boolean skipTest){
        if(skipTest) {
            return String.join(" ",
                    mvnPath,mavenCmd.toString(), "-DskipTests","-f",projectPath);
        }
        return String.join(" ",
                mvnPath,mavenCmd.toString(),"-f",projectPath);
    }
}
