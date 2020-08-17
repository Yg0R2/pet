package com.yg0r2.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableJpaRepositories(basePackages = {"com.yg0r2.pet.dao", "com.yg0r2.user.dao"})
@EnableRedisHttpSession
@EntityScan(basePackages = {"com.yg0r2.pet.dao.model", "com.yg0r2.user.dao.model"})
@SpringBootApplication(scanBasePackages = {"com.yg0r2"})
public class PetApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetApplication.class, args);
    }

}
