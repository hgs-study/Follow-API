package com.followproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@SpringBootApplication
public class FollowProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FollowProjectApplication.class, args);
    }

}
