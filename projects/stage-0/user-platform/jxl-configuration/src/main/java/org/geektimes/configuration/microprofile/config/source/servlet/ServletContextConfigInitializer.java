package org.geektimes.configuration.microprofile.config.source.servlet;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.geektimes.configuration.microprofile.config.constant.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public class ServletContextConfigInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver= ConfigProviderResolver.instance();

        ServletContextConfigSource servletContextConfigSource = new ServletContextConfigSource(servletContext);
        ConfigBuilder configBuilder = configProviderResolver.getBuilder();
        //设置classLoader
        configBuilder.forClassLoader(classLoader);
        //初始化默认数据源
        configBuilder.addDefaultSources();
        //初始化SPI 转换器
        configBuilder.addDiscoveredConverters();
        //初始化自定义数据源
        configBuilder.withSources(servletContextConfigSource);
        Config config = configBuilder.build();
        configProviderResolver.registerConfig(config, classLoader);

        //放进servlet 上下文
        servletContext.setAttribute(Constants.GLOBAL_CONFIG, config);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
