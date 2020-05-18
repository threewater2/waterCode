package xyz.threewater.action;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来保存下一步执行完成的监听器
 */
@Component
public class StepCenter {
    private List<StepOverListener> stepOverListeners=new ArrayList<>();

    public void addStepOverListener(StepOverListener stepOverListener){
        this.stepOverListeners.add(stepOverListener);
    }


    public void stepOver(int linNumber, String fullClassName, String fileName){
        stepOverListeners.forEach(s-> s.onStepOver(linNumber,fullClassName,fileName));
    }
}
