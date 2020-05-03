package xyz.threewater.debug;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class DebuggerTest {

    @Test
    public void breakPoint(){
        Debugger debugger=new JavaDebugger();
        String path="C:\\Users\\water\\IdeaProjects\\waterCode\\src\\test\\java\\xyz\\threewater\\debug\\MainClass.java";
        String fullClassName="xyz.threewater.debug.MainClass";
        int lineNumber=8;
        CodePosition codePosition=new CodePosition(path,fullClassName,lineNumber);
        debugger.addBreakPoint(codePosition);
        Map.Entry<String, String> value = debugger.getFieldValue("value");
        String value1 = value.getValue();
        assertEquals("value a",value1);
    }
}
