package xyz.threewater.run;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.threewater.console.command.CommandExecutor;
import xyz.threewater.console.command.CommandResult;
import xyz.threewater.exception.CommandExcuteException;

/**
 * 用来运行当前Java项目
 */
public interface Project {

    public void build();

    public void run(String className);
}
