package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author BOUGSID Ayoub
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }
}
