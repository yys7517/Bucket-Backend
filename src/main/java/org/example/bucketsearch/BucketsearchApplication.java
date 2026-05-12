package org.example.bucketsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BucketsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BucketsearchApplication.class, args);
    }

}
