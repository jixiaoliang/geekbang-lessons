package org.geekbang.thinking.in.spring.ioc.dependency.injection.test;

import org.geekbang.thinking.in.spring.ioc.dependency.injection.UserHolder;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author jixiaoliang
 * @since 2021/03/25
 **/
public class ApiDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();

        String path = "classpath:/META-INF/dependency-lookup-context.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        xmlBeanDefinitionReader.loadBeanDefinitions(path);

        beanFactory.register(ApiDependencyConstructorInjectionDemo.class);
        beanFactory.refresh();
       // System.out.println(beanFactory.getBean(User.class));
        System.out.println(beanFactory.getBean(UserHolder.class));

        beanFactory.close();
    }

    @Bean
    private UserHolder initUserHolder(){
        Thread.currentThread().getContextClassLoader();
        User u = new User();
        u.setName("jxl");
        return new UserHolder(u);
    }

   // @Bean
    private UserHolder initUserHolder(User user){
        return new UserHolder(user);
    }
}
