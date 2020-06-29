package xyz.threewater.plugin.maven.praser;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class DependencyTreeResolver {

    public Node resolveAsNode(File file){
        InputType type = InputType.TEXT;
        try {
            Reader r = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file.getAbsolutePath())));
            Parser parser = type.newParser();
            return parser.parse(r);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void resolveNode(Node node){
        callBack(node);
        for(Node child:node.getChildNodes()){
            resolveNode(child);
        }
    }

    protected abstract void callBack(Node path);
}
