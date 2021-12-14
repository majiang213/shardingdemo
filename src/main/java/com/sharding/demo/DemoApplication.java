package com.sharding.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sharding")
public class DemoApplication {

    public static void main(String[] arg) {
        SpringApplication.run(DemoApplication.class, arg);
    }

}
