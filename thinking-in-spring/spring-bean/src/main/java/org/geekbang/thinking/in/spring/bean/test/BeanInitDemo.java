package org.geekbang.thinking.in.spring.bean.test;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author jixiaoliang
 * @since 2021/03/25
 **/
public class BeanInitDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext conf = new AnnotationConfigApplicationContext();
        conf.register(BeanInitDemo.class);

        conf.refresh();

        System.out.println(conf.getBean(User.class));
        conf.close();
    }

    @Bean
    @Lazy
    public User initUser(){
        System.out.println("初始化user :initUser");
        return  User.createUser();
    }
}
