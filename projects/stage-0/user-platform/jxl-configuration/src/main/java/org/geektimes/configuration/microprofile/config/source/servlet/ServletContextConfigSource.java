package org.geektimes.configuration.microprofile.config.source.servlet;

import org.geektimes.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public class ServletContextConfigSource extends MapBasedConfigSource {
    Logger logger = Logger.getLogger(ServletContextConfigSource.class.getName());

    private  static ServletContext servletContext;

    private Map<String, String> properties;

    public ServletContextConfigSource(ServletContext servletContext){
        super("ServletContextConfigSource", 300);
        this.servletContext = servletContext;
        initConfig();
    }

    private void initConfig(){
        logger.info("初始化 ServletContextConfigSource 开始");
        Enumeration<String> enumeration= servletContext.getInitParameterNames();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = servletContext.getInitParameter(key);
            properties.put(key, value);
            logger.info("初始化 ServletContextConfigSource " + key + "=" + value);
        }
    }

    @Override
    protected void prepareConfigData(Map properties) throws Throwable {
        this.properties =properties;
    }


}
