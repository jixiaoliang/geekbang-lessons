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

    private Map<String, String> configData;

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
        configData = Maps.newHashMap();
    }


    /**
     * 获取配置数据 Map
     *
     * @return 不可变 Map 类型的配置数据
     */
    public final Map<String, String> getProperties() {
        return Collections.unmodifiableMap(getConfigData());
    }

    protected Map<String, String> getConfigData() {
        try {
            if (configData.isEmpty()) {
                prepareConfigData(configData);
            }
        } catch (Throwable cause) {
            throw new IllegalStateException("准备配置数据发生错误", cause);
        }
        return configData;
    }


    protected abstract void prepareConfigData(Map properties) throws Throwable;

    @Override
    public Set<String> getPropertyNames() {
        return configData.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return configData.get(propertyName);
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
