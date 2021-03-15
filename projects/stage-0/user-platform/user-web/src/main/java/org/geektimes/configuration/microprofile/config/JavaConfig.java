package org.geektimes.configuration.microprofile.config;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.*;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class JavaConfig implements Config {

    private final List<ConfigSource> configSourceList = Lists.newArrayList();

    private final Comparator<ConfigSource> comparator = (o1, o2) -> Integer.compare(o2.getOrdinal(), o1.getOrdinal());

    private Converter converter;

    public JavaConfig(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceLoader<ConfigSource> configSources = ServiceLoader.load(ConfigSource.class, classLoader);
        configSources.forEach(configSourceList::add);
        configSourceList.sort(comparator);

        ServiceLoader<Converter> converters =  ServiceLoader.load(Converter.class, classLoader);

        converter = converters.iterator().next();
    }


    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {

        return (T)converter.convert(getPropertiesValue(propertyName));
    }

    private String getPropertiesValue(String propertyName){
        for (ConfigSource configSource : configSourceList) {
            String value = configSource.getValue(propertyName);
            if(StringUtils.isNotEmpty(value)){
                return value;
            }
        }
        return null;
    }
    @Override
    public ConfigValue getConfigValue(String propertyName) {
        ConfigValue configValue = new ConfigValue(){

            @Override
            public String getName() {
                return propertyName;
            }

            @Override
            public String getValue() {
                return getPropertiesValue(propertyName);
            }

            @Override
            public String getRawValue() {
                return null;
            }

            @Override
            public String getSourceName() {
                return null;
            }

            @Override
            public int getSourceOrdinal() {
                return 0;
            }
        };

        return configValue;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.empty();
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return Collections.unmodifiableList(configSourceList);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
