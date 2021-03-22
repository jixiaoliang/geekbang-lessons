package org.geektimes.configuration.microprofile.config.source;

import com.google.common.collect.Lists;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.geektimes.configuration.microprofile.config.compair.ConfigSourceOrdinalComparator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public class ConfigSources implements Iterable<ConfigSource> {

    private boolean addedDefaultConfigSources;

    private boolean addedDiscoveredConfigSources;

    private List<ConfigSource> configSources = new LinkedList<>();

    private ClassLoader classLoader;

    public ConfigSources(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void addDefaultConfigSources() {
        if (addedDefaultConfigSources) {
            return;
        }
        addDefaultConfigSources(DefaultPropertiesConfigSource.class,
                JavaSystemPropertiesConfigSource.class,
                OperationSystemEnvironmentVariablesConfigSource.class
        );
        addedDefaultConfigSources =true;
    }

    public void addDiscoveredConfigSources(){
        if (addedDiscoveredConfigSources) {
            return;
        }
        addDefaultConfigSources(ServiceLoader.load(ConfigSource.class,classLoader));
        addedDiscoveredConfigSources=true;
    }

    public void addDefaultConfigSources(Class<? extends ConfigSource>... configSources) {
        addDefaultConfigSources(Stream.of(configSources)
                .map(this::newInstance
                ).collect(Collectors.toSet()));
    }

    public void addDefaultConfigSources(ConfigSource... configSources) {
        addDefaultConfigSources(Lists.newArrayList(configSources));
    }

    private void addDefaultConfigSources(Iterable<ConfigSource> configSources) {
        configSources.forEach(item -> this.configSources.add(item));
        Collections.sort(this.configSources, ConfigSourceOrdinalComparator.INSTANCE);
    }

    private ConfigSource newInstance(Class<? extends ConfigSource> configSourceClass) {
        ConfigSource instance = null;
        try {
            instance = configSourceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        return instance;
    }

    @Override
    public Iterator<ConfigSource> iterator() {
        return configSources.iterator();
    }


    public boolean isAddedDefaultConfigSources() {
        return addedDefaultConfigSources;
    }

    public void setAddedDefaultConfigSources(boolean addedDefaultConfigSources) {
        this.addedDefaultConfigSources = addedDefaultConfigSources;
    }

    public boolean isAddedDiscoveredConfigSources() {
        return addedDiscoveredConfigSources;
    }

    public void setAddedDiscoveredConfigSources(boolean addedDiscoveredConfigSources) {
        this.addedDiscoveredConfigSources = addedDiscoveredConfigSources;
    }


    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
