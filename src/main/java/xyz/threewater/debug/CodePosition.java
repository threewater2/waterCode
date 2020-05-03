package xyz.threewater.debug;

public class CodePosition {
    private String sourceCodePath;

    private String fullClassName;

    private int line;

    public CodePosition(String sourceCodePath, String fullClassName, int line) {
        this.sourceCodePath = sourceCodePath;
        this.fullClassName = fullClassName;
        this.line = line;
    }

    public String getSourceCodePath() {
        return sourceCodePath;
    }

    public void setSourceCodePath(String sourceCodePath) {
        this.sourceCodePath = sourceCodePath;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
