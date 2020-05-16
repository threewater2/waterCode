package xyz.threewater.action;

import xyz.threewater.debug.CodePosition;

@FunctionalInterface
public interface ResumListener {
    void resumed(CodePosition codePosition);
}
