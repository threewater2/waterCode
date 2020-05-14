package xyz.threewater.action;

import org.springframework.stereotype.Component;
import xyz.threewater.debug.BreakPointBean;

import java.util.ArrayList;
import java.util.List;

@Component
public class BreakPointCenter {
    private final List<BreakPointRemoveListener> breakPointRemoveListeners=new ArrayList<>();
    private final List<BreakPointAddListener> breakPointAddListeners=new ArrayList<>();
    public void addBreakPointRemoveListener(BreakPointRemoveListener breakPointRemoveListener){
        breakPointRemoveListeners.add(breakPointRemoveListener);
    }

    public void addBreakPointAddListener(BreakPointAddListener breakPointAddListener){
        breakPointAddListeners.add(breakPointAddListener);
    }

    public void breakPointRemoved(BreakPointBean breakPointBean){
        breakPointRemoveListeners.forEach(a->a.breakPointRemoved(breakPointBean));
    }

    public void breakPointAdded(BreakPointBean breakPointBean){
        breakPointAddListeners.forEach(a->a.breakPointAdded(breakPointBean));
    }

}
