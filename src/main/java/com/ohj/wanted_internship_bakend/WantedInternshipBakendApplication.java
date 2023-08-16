package com.ohj.wanted_internship_bakend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WantedInternshipBakendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WantedInternshipBakendApplication.class, args);
    }

}
