package xyz.threewater.debug;

import org.springframework.stereotype.Component;

@Component
public class DebugStatus {
    private boolean isStopped=true;
    private CodePosition codePosition;
    private boolean vmExit=true;

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public CodePosition getCodePosition() {
        return codePosition;
    }

    public void setCodePosition(CodePosition codePosition) {
        this.codePosition = codePosition;
    }

    public boolean isVmExit() {
        return vmExit;
    }

    public void setVmExit(boolean vmExit) {
        this.vmExit = vmExit;
    }
}
