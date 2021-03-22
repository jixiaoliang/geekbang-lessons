package org.geektimes.configuration.microprofile.config.source.listener;

import org.eclipse.microprofile.config.Config;
import org.geektimes.configuration.microprofile.config.constant.Constants;
import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class ComponentContextInitializerListener implements ServletContextListener {

    private final Logger logger = Logger.getLogger(ServletContextConfigInitializer.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(servletContext);

        //放进servlet 上下文
        Config config = (Config) servletContext.getAttribute(Constants.GLOBAL_CONFIG);

        String appNameKey = "application.name";
        String  appName = config.getValue(appNameKey, String.class);
        logger.info("应用: ["+appName +"] 初始化完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // ComponentContext.getInstance().destroy();
    }
}
