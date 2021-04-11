package org.geektimes.serializer;

import javax.cache.CacheException;

/**
 * @author jixiaoliang
 * @since 2021/04/11
 **/
public interface Serializer<V> {

    /**
     * 序列化
     *
     * @param obj 待序列化对象
     * @return 序列化后的 字节数组
     * @throws CacheException 异常时抛出
     */
    byte[] serialize(Object obj) throws CacheException;

    /**
     * 返序列化
     *
     * @param bytes 字节数组
     * @return 反序列化后的对象
     * @throws CacheException 异常时抛出
     */
    V deserialize(byte[] bytes, Class<V> c) throws CacheException;


}
