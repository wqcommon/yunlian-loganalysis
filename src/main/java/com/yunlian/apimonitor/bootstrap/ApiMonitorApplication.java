package com.yunlian.apimonitor.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yunlian.apimonitor"})
public class ApiMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMonitorApplication.class,args);
    }
}
