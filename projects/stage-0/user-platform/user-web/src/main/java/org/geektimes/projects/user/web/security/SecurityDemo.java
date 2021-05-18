package org.geektimes.projects.user.web.security;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jixiaoliang
 * @since 2021/05/12
 **/
//@SpringBootApplication
//@RestController
public class SecurityDemo {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo.class, args);
    }

    // @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
