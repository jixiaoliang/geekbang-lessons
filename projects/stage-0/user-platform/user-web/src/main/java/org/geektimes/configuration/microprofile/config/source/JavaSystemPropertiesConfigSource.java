package org.geektimes.configuration.microprofile.config.source;

import com.google.common.collect.Maps;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class JavaSystemPropertiesConfigSource implements ConfigSource {

    private  final Map<String,String> properties ;

    public JavaSystemPropertiesConfigSource(){
        properties = new HashMap(System.getProperties());
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "Java System Properties";
    }

    @Override
    public int getOrdinal() {
        return 1;
    }
}
