package xyz.threewater.run;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ClassPath {
    Set<String> classPath=new HashSet<>();

    public void addClassPath(String path){
       classPath.add(path);
    }

    public Set<String> getClassPathList(){
        return classPath;
    }

    public abstract String getClassPath();

    public abstract File storeClassPath();

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        InputType type = InputType.TEXT;
        Reader r = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\water\\Desktop\\3.txt")));
        Parser parser = type.newParser();
        Node tree = parser.parse(r);
        System.out.println(tree);
    }
}
