package xyz.threewater.console.command;

public class CommandResult{
    public CommandResult(String outPut, int exitCode) {
        this.outPut = outPut;
        this.exitCode = exitCode;
    }

    private String outPut;
    private int exitCode;

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}