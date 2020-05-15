package xyz.threewater.action;

import xyz.threewater.debug.BreakPointBean;

@FunctionalInterface
public interface BreakPointPauseListener {
    void onBreakPointPaused(BreakPointBean breakPointBean);
}
