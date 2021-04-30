package com.example.midtermproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class MidTermProjectApplication {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new
                AnnotationConfigApplicationContext(JavaConfig.class);
        SystemBean systemBean = (SystemBean)ctx.getBean("systemBean");

        systemBean.runSystem();

        ctx.close();
    }

}
