package xyz.threewater.editor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InputCacheTest {
    @Test
    public void testRemove(){
        InputCache inputCache=new InputCache();
        inputCache.addCache("qw");
        inputCache.deleteCache();
        assertEquals(inputCache.getCache().get(),"q");
        inputCache.clearCache();
        inputCache.addCache("a");
        assertEquals(inputCache.getCache().get(),"a");
        inputCache.deleteCache();
        assertFalse(inputCache.getCache().isPresent());
    }
}
