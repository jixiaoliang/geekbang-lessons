package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class JavaLocalFilePropertiesConfigSource implements ConfigSource {

    private final Properties properties = new Properties();

    public JavaLocalFilePropertiesConfigSource() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            properties.load(classLoader.getResourceAsStream("customer-config.properties"));
        } catch (IOException e) {

        }

    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet().stream().map(Objects::toString).collect(Collectors.toSet());
    }

    @Override
    public String getValue(String propertyName) {
        return properties.getProperty(propertyName);
    }

    @Override
    public String getName() {
        return "JAVA Local File Properties";
    }

    @Override
    public int getOrdinal() {
        return 0;
    }
}
