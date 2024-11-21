package com.arturk.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StorageMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageMsApplication.class, args);
    }
}
