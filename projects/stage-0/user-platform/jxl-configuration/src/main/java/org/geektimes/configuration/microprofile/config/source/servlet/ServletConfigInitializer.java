package org.geektimes.configuration.microprofile.config.source.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public class ServletConfigInitializer implements ServletContainerInitializer {
    Logger logger = Logger.getLogger(ServletConfigInitializer.class.getName());

    @Override
    public void onStartup(Set<Class<?>> classSet, ServletContext servletContext) throws ServletException {
        logger.info("onStartup classSet:"+classSet);
        servletContext.addListener(ServletContextConfigInitializer.class);
    }
}
