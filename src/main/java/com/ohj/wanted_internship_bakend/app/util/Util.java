package com.ohj.wanted_internship_bakend.app.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 *
 * Desc : Util Class
 */
@Configuration
public class Util {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
