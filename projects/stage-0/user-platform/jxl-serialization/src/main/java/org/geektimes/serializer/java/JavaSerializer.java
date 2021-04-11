package org.geektimes.serializer.java;

import org.geektimes.serializer.Serializer;

import javax.cache.CacheException;
import java.io.*;

/**
 * @author jixiaoliang
 * @since 2021/04/11
 **/
public class JavaSerializer<V> implements Serializer<V> {

    // 是否可以抽象出一套序列化和反序列化的 API
    public byte[] serialize(Object value) throws CacheException {
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new CacheException(e);
        }
        return bytes;
    }

    @Override
    public V deserialize(byte[] bytes, Class c) throws CacheException {
        if (bytes == null) {
            return null;
        }
        V value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = (V) objectInputStream.readObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
        return value;
    }


}
