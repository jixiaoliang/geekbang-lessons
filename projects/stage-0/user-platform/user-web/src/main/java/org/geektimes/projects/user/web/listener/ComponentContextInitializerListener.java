package org.geektimes.projects.user.web.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.ConfigValue;
import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class ComponentContextInitializerListener implements ServletContextListener {
    Logger logger = Logger.getLogger(ComponentContextInitializerListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(sce.getServletContext());

        Config config = ConfigProvider.getConfig();

        String appNameKey = "application.name";
        String  appName = config.getValue(appNameKey, String.class);
        logger.info("应用:"+appName +"初始化完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       // ComponentContext.getInstance().destroy();
    }
}
