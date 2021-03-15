package org.geektimes.configuration.microprofile.config.Converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class BaseTypeConvert<T> implements Converter<T> {

    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        try {
            Method method = getClass().getMethod("convert", String.class);
            AnnotatedType type = method.getAnnotatedReturnType();
            String typeName = type.getType().getTypeName();
            System.out.println("typeName:" + typeName+" "+type.getClass());
            return (T)value;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
