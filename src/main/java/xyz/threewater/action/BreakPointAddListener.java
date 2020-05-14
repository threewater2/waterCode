package xyz.threewater.action;

import xyz.threewater.debug.BreakPointBean;

@FunctionalInterface
public interface BreakPointAddListener {
    void breakPointAdded(BreakPointBean breakPointBean);
}
