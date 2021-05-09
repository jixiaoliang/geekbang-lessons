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

    private  final ServletContext servletContext;

    public ServletContextConfigSource(ServletContext servletContext){
        super("ServletContextConfigSource", 300);
        this.servletContext = servletContext;
    }



    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        Enumeration<String> parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            configData.put(parameterName, servletContext.getInitParameter(parameterName));
        }
    }

}
