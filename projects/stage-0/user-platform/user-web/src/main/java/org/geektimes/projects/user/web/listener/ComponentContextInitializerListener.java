package org.geektimes.projects.user.web.listener;

import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ComponentContextInitializerListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       // ComponentContext.getInstance().destroy();
    }
}
