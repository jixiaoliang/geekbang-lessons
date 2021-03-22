package org.geektimes.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource {


    public JavaSystemPropertiesConfigSource() {
        super("JavaSystemPropertiesConfigSource", 200);
    }

    @Override
    protected void prepareConfigData(Map properties) throws Throwable {
        properties.putAll(System.getProperties());
    }

}
