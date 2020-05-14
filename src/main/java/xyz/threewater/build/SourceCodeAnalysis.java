package xyz.threewater.build;

import xyz.threewater.utils.FileUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceCodeAnalysis {
    private File sourceFile;
    public SourceCodeAnalysis(File sourceFile){
        this.sourceFile=sourceFile;
    }

    public String getFullClassName(){
        Pattern packagePattern=Pattern.compile("package (.*?);");
        Pattern classNamePattern=Pattern.compile("public class (.*?)\\{");
        String sourceCode = FileUtil.file2String(sourceFile);
        // 内容 与 匹配规则 的测试
        Matcher matcher = packagePattern.matcher(sourceCode);
        String packageName;
        String className;
        if(matcher.find()) {
            packageName = matcher.group(1);
        }else return null;
        Matcher matcher1 = classNamePattern.matcher(sourceCode);
        if(matcher1.find()){
            className=matcher1.group(1);
        }else return null;
        String result=packageName+"."+className;
        return result.trim();
    }
}
