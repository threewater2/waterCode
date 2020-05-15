package xyz.threewater.action;

import javax.annotation.Nullable;

@FunctionalInterface
public interface StepOverListener {
    void onStepOver(int lineNumber, String fullClassName, @Nullable String fileName);
}
