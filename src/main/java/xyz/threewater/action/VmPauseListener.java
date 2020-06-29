package xyz.threewater.action;

import com.sun.jdi.event.Event;

public interface VmPauseListener {
    void vmPause(Event event);
}
