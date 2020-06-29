package xyz.threewater.action;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DebugCenter {
    private final List<DebugListener> listeners=new ArrayList<>();

    public void debugStarted(){
        listeners.forEach(DebugListener::debugStarted);
    }

    public void debugFinished(){
        listeners.forEach(DebugListener::debugFinished);
    }

    public void addDebugListener(DebugListener debugListener){
        listeners.add(debugListener);
    }
}
