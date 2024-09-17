package com.hassan.pets.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCrypt {


    @Bean
    public static BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder(12);
    }
}
