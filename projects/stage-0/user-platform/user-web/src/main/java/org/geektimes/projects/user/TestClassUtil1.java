package org.geektimes.projects.user;

import org.apache.commons.lang.ClassUtils;

/**
 * @author jixiaoliang
 * @since 2021/03/19
 **/
public class TestClassUtil1 {
    public static void main(String[] args) {
        System.out.println(ClassUtils.wrappersToPrimitives(new Class[]{Integer.class})[0]);
        System.out.println(ClassUtils.wrappersToPrimitives(new Class[]{Integer.class})[0] == int.class);
        System.out.println(Integer.class+":"+ int.class);
    }
}
