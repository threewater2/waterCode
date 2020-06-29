package xyz.threewater.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用来在非spring环境下获取spring bean
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext=applicationContext;
    }

    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
}
