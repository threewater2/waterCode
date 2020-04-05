package xyz.threewater.enviroment;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JavaFxComponent {
    private Map<String,Object> map=new HashMap<>();

    public Object get(String id){
        return map.get(id);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String id,Class<T> type){
        Object bean=map.get(id);
        if(type != null && !type.isInstance(bean)){
            throw new RuntimeException("not found type component of:"+type.getName());
        }
        return (T)bean;
    }

    public void set(String id,Object value){
        map.put(id,value);
    }

}
