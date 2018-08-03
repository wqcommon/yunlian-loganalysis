package com.yunlian.loganalysis.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yunlian.loganalysis"})
public class LogAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogAnalysisApplication.class,args);
    }
}
