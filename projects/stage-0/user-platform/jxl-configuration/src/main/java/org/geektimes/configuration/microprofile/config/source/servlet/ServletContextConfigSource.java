package org.geektimes.configuration.microprofile.config.source.servlet;

import org.geektimes.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public class ServletContextConfigSource extends MapBasedConfigSource {

    private ServletContext servletContext;

    public ServletContextConfigSource(ServletContext servletContext){
        super("ServletContextConfigSource", 300);
        this.servletContext = servletContext;
    }

    @Override
    protected void prepareConfigData(Map properties) throws Throwable {
        Enumeration<String> enumeration= servletContext.getInitParameterNames();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            properties.put(key, servletContext.getInitParameter(key));
        }
    }
}
