package org.geektimes.configuration.microprofile.config;

import com.google.common.collect.Lists;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.Converters;
import org.geektimes.configuration.microprofile.config.source.ConfigSources;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class DefaultConfig implements Config {
    private Logger logger = Logger.getLogger("DefaultConfig");

    private final ConfigSources configSources;

    private final Converters converters ;

    public DefaultConfig(ConfigSources configSources, Converters converters) {
        this.configSources = configSources;
        this.converters = converters;
        configSources.forEach(ConfigSource::getProperties);
    }

    /*public DefaultConfig(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceLoader<ConfigSource> configSources = ServiceLoader.load(ConfigSource.class, classLoader);
        configSources.forEach(configSourceList::add);
        configSourceList.sort(comparator);

        ServiceLoader<Converter> converters =  ServiceLoader.load(Converter.class, classLoader);

        converter = converters.iterator().next();
    }
*/

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        Converter<T> converter = doGetConvert(propertyType);
        return converter == null ? null : converter.convert(getPropertiesValue(propertyName));
    }

    private <T> Converter<T> doGetConvert(Class<T> propertyType) {
        List<Converter> converterList = converters.getConverters(propertyType);
        return converterList.isEmpty() ? null : converterList.get(0);
    }

    private String getPropertiesValue(String propertyName){
        for (ConfigSource configSource : configSources) {
            String value = configSource.getValue(propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.ofNullable(getValue(propertyName, propertyType));
    }

    @Override
    public Iterable<String> getPropertyNames() {
        //触发初始化
        return StreamSupport.stream(configSources.spliterator(),false)
                .map(x->{
                    //logger.info(x.getName()+"==>"+x.getPropertyNames());
                    return x.getPropertyNames();
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
       return Collections.unmodifiableList(Lists.newArrayList(configSources));
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }

    public static void main(String[] args) {
        try {
            int i =3/0;
        } finally {
            System.out.println("ok");
        }
    }
}
