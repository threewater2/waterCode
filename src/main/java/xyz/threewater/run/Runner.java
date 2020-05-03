package xyz.threewater.run;

import xyz.threewater.exception.CommandExcuteException;

public interface Runner {
    void runClass(String classPath,String fullClassName) throws CommandExcuteException;
}
