package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Objects;
import java.util.Properties;

/**
 * @author jixiaoliang
 * @since 2021/04/11
 **/
public class LettuceCacheManager extends AbstractCacheManager {

    private StatefulRedisConnection<String, String> redisConnect;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        redisConnect = RedisClient.create(uri.toString()).connect();
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        return new LettuceCache(this, cacheName, configuration, redisConnect);
    }


    @Override
    protected void doClose() {
        if(Objects.nonNull(redisConnect)) {
            redisConnect.close();
        }
    }
}
