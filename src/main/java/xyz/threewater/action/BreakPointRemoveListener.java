package xyz.threewater.action;

import xyz.threewater.debug.BreakPointBean;

@FunctionalInterface
public interface BreakPointRemoveListener {
    void breakPointRemoved(BreakPointBean breakPointBean);
}
