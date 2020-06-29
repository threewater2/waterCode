package xyz.threewater.debug;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BreakPointHolderText {
    @Autowired
    private BreakPointHolder breakPointHolder;

    @Test
    public void getWaitingBreakPoint(){
        breakPointHolder.addBreakPoint("xyz.threewater.DebugDemo",10);
        breakPointHolder.addBreakPoint("xyz.threewater.DebugDemo",11);
        List<BreakPointBean> waitingBreakPoints = breakPointHolder.getWaitingBreakPoints("xyz.threewater.DebugDemo");
        assertEquals(2,waitingBreakPoints.size());
    }
}
