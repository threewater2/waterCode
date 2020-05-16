package xyz.threewater.action;

import org.springframework.stereotype.Component;
import xyz.threewater.debug.CodePosition;

import java.util.ArrayList;
import java.util.List;

@Component
public class VMCenter {
    private final List<VmListener> listeners=new ArrayList<>();
    private final List<ResumListener> resumeListeners =new ArrayList<>();

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
}
