package xyz.threewater.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * user keyBoard input cache
 */
public class InputCache {

    private Logger logger= LoggerFactory.getLogger(InputCache.class);

    private StringBuilder cache=new StringBuilder();

    public void addCache(String newChar){
        cache.append(newChar);
        logger.debug("cache added:{}",cache.toString());
    }

    public void deleteCache(){
        if(cache.length()>0){
            cache.delete(cache.length()-1,cache.length());
        }
        logger.debug("cache deleted:{}",cache.toString());
    }

    public void clearCache(){
        cache.delete(0,cache.length());
        logger.debug("cache cleared");
    }

    public Optional<String> getCache(){
        if(cache.toString().equals("")){
            return Optional.empty();
        }else {
            return Optional.of(cache.toString());
        }
    }
}

