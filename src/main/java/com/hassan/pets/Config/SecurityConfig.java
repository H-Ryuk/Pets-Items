package com.hassan.pets.Config;


import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;


    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }




    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(BCrypt.encoder());
        return provider;
    }


    @Bean
    public SecurityFilterChain SecurityFilter(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.GET, "/api/item/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/item").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/item").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/item").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/item").hasRole("ADMIN");
//                    req.requestMatchers("/api/item").hasAuthority("CUSTOMER");
                    req.anyRequest().authenticated();
                })
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authProvider())
                .build();
    }


}
