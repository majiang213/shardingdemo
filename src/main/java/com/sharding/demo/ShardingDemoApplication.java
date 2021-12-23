package com.sharding.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan("com.sharding")
@MapperScan(basePackages = "com.sharding.demo.dao")
public class ShardingDemoApplication implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(ShardingDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("DATASOURCE = " + dataSource);
    }

}
