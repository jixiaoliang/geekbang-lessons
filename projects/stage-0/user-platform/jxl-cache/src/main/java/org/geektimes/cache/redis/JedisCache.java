package org.geektimes.cache.redis;

import org.geektimes.cache.AbstractCache;
import org.geektimes.serializer.Serializer;
import org.geektimes.serializer.java.JavaSerializer;
import redis.clients.jedis.Jedis;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Iterator;

public class JedisCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {


    private Serializer<Object> serializer = new JavaSerializer<>();


    private final Jedis jedis;

    public JedisCache(CacheManager cacheManager, String cacheName,
                      Configuration<K, V> configuration, Jedis jedis) {
        super(cacheManager, cacheName, configuration);
        this.jedis = jedis;
    }

    @Override
    protected V doGet(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        return doGet(keyBytes);
    }

    protected V doGet(byte[] keyBytes) {
        byte[] valueBytes = jedis.get(keyBytes);
        V value = deserialize(valueBytes);
        return value;
    }

    @Override
    protected V doPut(K key, V value) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        byte[] valueBytes = serialize(value);
        V oldValue = doGet(keyBytes);
        jedis.set(keyBytes, valueBytes);
        return oldValue;
    }

    @Override
    protected V doRemove(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        V oldValue = doGet(keyBytes);
        jedis.del(keyBytes);
        return oldValue;
    }

    @Override
    protected void doClear() throws CacheException {

    }

    @Override
    protected Iterator<Entry<K, V>> newIterator() {
        return null;
    }

    @Override
    protected void doClose() {
        this.jedis.close();
    }

    // 是否可以抽象出一套序列化和反序列化的 API
    private byte[] serialize(Object value) throws CacheException {
        return  serializer.serialize(value);
    }

    private V deserialize(byte[] bytes) throws CacheException {
        return (V) serializer.deserialize(bytes, Object.class);
    }

}
