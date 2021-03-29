package org.geektimes.configuration.microprofile.config.servlet.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.util.logging.Logger;

public class ConfigServletRequestListener implements ServletRequestListener {

    private Logger logger = Logger.getLogger(ConfigServletRequestListener.class.getName());

    private static final ThreadLocal<Config> configThreadLocal = new ThreadLocal<>();

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("ServletContextConfigInitializer 执行初始化");

        ServletRequest request = sre.getServletRequest();
        ServletContext servletContext = request.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        Config config = configProviderResolver.getConfig(classLoader);
        configThreadLocal.set(config);
    }

    public static Config getConfig() {
        return configThreadLocal.get();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 防止 OOM
        configThreadLocal.remove();
    }

}
