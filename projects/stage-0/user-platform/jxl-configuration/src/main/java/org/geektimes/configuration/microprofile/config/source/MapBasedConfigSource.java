package org.geektimes.configuration.microprofile.config.source;

import com.google.common.collect.Maps;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public abstract class MapBasedConfigSource implements ConfigSource {

    private static Map<String, String> map;

    /**
     * 数据源名称
     */
    private final String name;

    /**
     * 优先级排序,越大优先级越高
     */
    private final int ordinal;


    public MapBasedConfigSource(String name, int ordinal) {
        this.ordinal = ordinal;
        this.name = name;
        map = getProperties();
    }

    public Map<String, String> getProperties() {
        Map<String, String> properties = Maps.newHashMap();
        try {
            prepareConfigData(properties);
        } catch (Throwable e) {
            throw new IllegalStateException("加载配置数据异常", e);
        }
        return Collections.unmodifiableMap(properties);
    }

    protected abstract void prepareConfigData(Map properties) throws Throwable;

    @Override
    public Set<String> getPropertyNames() {
        return map.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return map.get(propertyName);
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final int getOrdinal() {
        return ordinal;
    }
}
