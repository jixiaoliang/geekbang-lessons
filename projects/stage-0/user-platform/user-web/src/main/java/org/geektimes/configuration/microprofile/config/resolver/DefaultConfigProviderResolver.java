package org.geektimes.configuration.microprofile.config.resolver;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class DefaultConfigProviderResolver extends ConfigProviderResolver {



    @Override
    public Config getConfig() {
        return getConfig(null);
    }

    @Override
    public Config getConfig(ClassLoader loader) {
        if(Objects.isNull(loader)){
            loader = Thread.currentThread().getContextClassLoader();
        }

        ServiceLoader<Config> configs = ServiceLoader.load(Config.class, loader);
        Iterator<Config> iterator = configs.iterator();
        while (iterator.hasNext()){
            return iterator.next();
        }
        throw new IllegalStateException("no config implements found!!!");
    }

    @Override
    public ConfigBuilder getBuilder() {
        return null;
    }

    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {

    }

    @Override
    public void releaseConfig(Config config) {

    }
}
