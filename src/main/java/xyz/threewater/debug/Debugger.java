package xyz.threewater.debug;

import sun.text.CodePointIterator;
import xyz.threewater.function.Position;

import java.util.Map;

public interface Debugger {

    void startDebug(String mainClassPath);

    void stopDebug();

    void addBreakPoint(CodePosition position);

    void removeBreakPoint(CodePosition position);

    void nextStep();

    CodePosition currentPosition();

    Map<String,String> fieldValues();

    Map.Entry<String,String> getFieldValue(String fieldName);

    String evaluateExpression(String expressions);
}
