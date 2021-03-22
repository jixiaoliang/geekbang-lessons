package org.geektimes.configuration.microprofile.config.resolver;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.geektimes.configuration.microprofile.config.DefaultConfigBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class DefaultConfigProviderResolver extends ConfigProviderResolver {

    private final Map<ClassLoader, Config> configMap = new ConcurrentHashMap<>();


    @Override
    public Config getConfig() {
        return getConfig(null);
    }

    @Override
    public Config getConfig(ClassLoader loader) {
        return configMap.computeIfAbsent(loader, x -> newConfigBuilder(loader).build());
    }

    private ClassLoader resolverClassLoader(ClassLoader classLoader){
        return classLoader==null?Thread.currentThread().getContextClassLoader():classLoader;
    }

    private ConfigBuilder newConfigBuilder(ClassLoader classLoader){
       return new DefaultConfigBuilder(resolverClassLoader(classLoader));
    }

    @Override
    public ConfigBuilder getBuilder() {
        return newConfigBuilder(null);
    }

    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {
        configMap.put(classLoader, config);
    }

    @Override
    public void releaseConfig(Config config) {
        configMap.keySet().forEach(configMap::remove);
        // 用clear 有啥不同
    }
}
