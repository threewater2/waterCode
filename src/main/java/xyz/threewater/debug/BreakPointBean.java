package xyz.threewater.debug;

/**
 * 用来表示断点的类
 */
public class BreakPointBean {
    private String fullClassName;
    private int line;
    private String fileName;
    private boolean isUsed=false;

    public BreakPointBean(String fullClassName, int line) {
        this.fullClassName = fullClassName;
        this.line = line;
    }

    public BreakPointBean(int line,String fullClassName,String fileName) {
        this.fullClassName = fullClassName;
        this.line = line;
        this.fileName=fileName;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BreakPointBean){
            BreakPointBean breakPointBean=(BreakPointBean)obj;
            boolean same1=breakPointBean.getFullClassName().equals(fullClassName);
            boolean same2=breakPointBean.getLine()==line;
            return same1&&same2;
        }
        return false;
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

    public boolean isUsed() {
        return isUsed;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "BreakPointBean{" +
                "fullClassName='" + fullClassName + '\'' +
                ", line=" + line +
                ", isUsed=" + isUsed +
                '}';
    }
}
