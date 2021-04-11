package org.geektimes.serializer.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.geektimes.serializer.Serializer;
import org.geektimes.serializer.function.ThrowableFunction;

import javax.cache.CacheException;

/**
 * @author jixiaoliang
 * @since 2021/04/11
 **/
public class JacksonSerializer<V> implements Serializer<V> {


    public static ObjectMapper jsonMapper = new ObjectMapper();


    public static String of(Object o) {
        return ThrowableFunction.execute(o, x -> jsonMapper.writeValueAsString(o));
    }

    @SuppressWarnings("unchecked")
    public static <T> T of(String json, Class<T> tClass) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        return ThrowableFunction.execute(json, x -> jsonMapper.readValue(json, tClass));
    }


    public static String toJson(Object obj) {
        if (null == obj) {
            return null;
        }

        return ThrowableFunction.execute(obj, x -> jsonMapper.writeValueAsString(obj));
    }

    public static void main(String[] args) {
        System.out.println(JacksonSerializer.toJson(1));
        System.out.println(JacksonSerializer.of("1", Integer.class));
    }

    @Override
    public byte[] serialize(Object obj) throws CacheException {
        return of(obj).getBytes();
    }

    @Override
    public V deserialize(byte[] bytes, Class c) throws CacheException {
        return (V) of(new String(bytes), c);
    }


}
