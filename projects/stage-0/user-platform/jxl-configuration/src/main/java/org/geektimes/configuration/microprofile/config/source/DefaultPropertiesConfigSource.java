package org.geektimes.configuration.microprofile.config.source;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class DefaultPropertiesConfigSource extends MapBasedConfigSource {

    private static final String configFileLocation = "META-INF/microprofile-config.properties";

    Logger logger = Logger.getLogger(DefaultPropertiesConfigSource.class.getName());


    public DefaultPropertiesConfigSource() {
        super("JavaLocalFilePropertiesConfigSource", 100);
    }

    @Override
    protected void prepareConfigData(Map map) throws Throwable{
        Properties properties = new Properties();
        URL url =  getClass().getClassLoader().getResource(configFileLocation);
        if(url == null){
            logger.warning("The default config file can't be found in the classpath :" + configFileLocation);
            return;
        }
        try(InputStream inputStream = url.openStream()){
            properties.load(inputStream);
            map.putAll(properties);
        }
    }


}
