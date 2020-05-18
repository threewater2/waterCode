package xyz.threewater.action;

import com.sun.jdi.event.Event;
import org.springframework.stereotype.Component;
import xyz.threewater.debug.CodePosition;
import xyz.threewater.editor.parse.Trie;

import java.util.ArrayList;
import java.util.List;

@Component
public class VMCenter {
    private final List<VmListener> listeners=new ArrayList<>();
    private final List<VmPauseListener> vmPauseListeners =new ArrayList<>();
    private final List<ResumListener> resumeListeners =new ArrayList<>();
    private final List<VariablesChangeListener> variablesChangeListeners=new ArrayList<>();

    public void addVmListener(VmListener vmListener){
        listeners.add(vmListener);
    }

    public void VmExited(){
        listeners.forEach(VmListener::vmExit);
    }

    public void resume(CodePosition codePosition){
        resumeListeners.forEach(r->r.resumed(codePosition));
    }

    public void addResumeListener(ResumListener resumListener){
        resumeListeners.add(resumListener);
    }

    public void vmPause(Event event){
        vmPauseListeners.forEach(vmPauseListener -> vmPauseListener.vmPause(event));
    }

    public void variablesChanged(List<String> variables){
        variablesChangeListeners.forEach(variablesChangeListener -> variablesChangeListener.variableChange(variables));
    }

    public void addVariablesChangedListener(VariablesChangeListener variablesChangeListener){
         variablesChangeListeners.add(variablesChangeListener);
    }

    public void addVmPauseListener(VmPauseListener vmPauseListener){
        vmPauseListeners.add(vmPauseListener);
    }
}
