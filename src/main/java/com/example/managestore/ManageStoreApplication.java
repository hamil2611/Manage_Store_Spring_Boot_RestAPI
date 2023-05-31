package com.example.managestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
public class ManageStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageStoreApplication.class, args);
    }

}
