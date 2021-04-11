package org.geektimes.cache.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.commons.lang.StringUtils;
import org.geektimes.cache.AbstractCache;
import org.geektimes.serializer.Serializer;
import org.geektimes.serializer.jackson.JacksonSerializer;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Iterator;

/**
 * @author jixiaoliang
 * @since 2021/04/11
 **/
public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {


    private StatefulRedisConnection<String, String> redisConnect;

    private Class valueTypeOfV;

    private Serializer jacksonSerializer ;


    public LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration, StatefulRedisConnection<String, String> redisConnect) {
        super(cacheManager, cacheName, configuration);
        this.redisConnect = redisConnect;
        valueTypeOfV = configuration.getValueType();
        jacksonSerializer = new JacksonSerializer<>();
    }

    @Override
    protected V doGet(K key) throws CacheException, ClassCastException {
        RedisCommands<String, String> redisCommands = redisConnect.sync();
        String value = redisCommands.get(buildKey(key));
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return (V) jacksonSerializer.deserialize(value.getBytes(), valueTypeOfV);
    }

    private String buildKey(K key) {
        byte[] bytes = jacksonSerializer.serialize(key);
       return new String(bytes);
    }

    @Override
    protected V doPut(K key, V value) throws CacheException, ClassCastException {
        RedisCommands<String, String> redisCommands = redisConnect.sync();
        V oldValue = doGet(key);
        redisCommands.set(JacksonSerializer.of(buildKey(key)),JacksonSerializer.of(value));
        return oldValue;
    }

    @Override
    protected V doRemove(K key) throws CacheException, ClassCastException {
        RedisCommands<String, String> redisCommands = redisConnect.sync();
        V oldValue = doGet(key);
        redisCommands.del(buildKey(key));
        return oldValue;
    }

    @Override
    protected void doClear() throws CacheException {
        RedisCommands<String, String> redisCommands = redisConnect.sync();
        //TODO 待实现
    }

    @Override
    protected Iterator<Entry<K, V>> newIterator() {
        return null;
    }
}
