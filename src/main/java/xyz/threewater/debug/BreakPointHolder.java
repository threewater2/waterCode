package xyz.threewater.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.action.BreakPointCenter;
import xyz.threewater.action.VMCenter;
import xyz.threewater.action.VmListener;

import java.util.*;

/**
 * 用来持有用户所打的断点
 */
@Component
public class BreakPointHolder {
    //键：全限定类名，值，该类上所有的断点
    private final Set<BreakPointBean> breakPoints=new HashSet<>();
    private final Logger logger= LoggerFactory.getLogger(BreakPointHolder.class);
    //事件中心
    private final BreakPointCenter breakPointCenter;

    public BreakPointHolder(BreakPointCenter breakPointCenter, VMCenter vmCenter) {
        this.breakPointCenter = breakPointCenter;
        vmCenter.addVmListener(this::recoverBreakPoints);
    }

    public void addBreakPoint(String fullClassName,int lineNumber){
        BreakPointBean breakPointBean = new BreakPointBean(fullClassName, lineNumber);
        breakPoints.add(breakPointBean);
        breakPointCenter.breakPointAdded(breakPointBean);
        logger.debug("add breakpoint:{},line:{}",fullClassName,lineNumber);

    }

    public void removeBreakPoint(String fullClassName,int lineNumber){
        logger.debug("remove breakpoint:{},line:{}",fullClassName,lineNumber);
        BreakPointBean breakPointBean = new BreakPointBean(fullClassName, lineNumber);
        breakPoints.remove(breakPointBean);
        breakPointCenter.breakPointRemoved(breakPointBean);
    }

    public List<BreakPointBean> getWaitingBreakPoints(String fullClassName){
        List<BreakPointBean> breakPointBeans=new ArrayList<>();
        breakPoints.forEach(breakPointBean -> {
            if(breakPointBean.getFullClassName().equals(fullClassName)&&!breakPointBean.isUsed()){
                breakPointBeans.add(breakPointBean);
            }
        });
        logger.debug("waiting {} breakPoints count:{}",fullClassName,breakPointBeans.size());
        return breakPointBeans;
    }

    public void recoverBreakPoints(){
        breakPoints.forEach(breakPointBean -> breakPointBean.setUsed(false));
        logger.debug("all breakpoints are reseted, size{}",breakPoints.size());
    }

    public Set<BreakPointBean> getAllBreakPoint(){
        return breakPoints;
    }
}
