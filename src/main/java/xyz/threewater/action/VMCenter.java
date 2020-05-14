package xyz.threewater.action;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VMCenter {
    private List<VmListener> listeners=new ArrayList<>();

    public void addVmListener(VmListener vmListener){
        listeners.add(vmListener);
    }

    public void VmExited(){
        listeners.forEach(VmListener::vmExit);
    }
}
