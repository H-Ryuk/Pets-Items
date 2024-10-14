package com.hassan.pets.Security;


import com.hassan.pets.Security.Encryption.BCrypt;
import com.hassan.pets.Security.Filters.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;
    private final BCrypt bCrypt;
    private final JwtFilter jwtFilter;




    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(bCrypt.encoder());
        return provider;
    }


    @Bean
    public SecurityFilterChain SecurityFilter(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/v1/user/login", "/api/v1/user/register").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/v1/item").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/v1/item").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v1/item").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v1/item").hasRole("ADMIN");
                    req.anyRequest().authenticated();
                })
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public AuthenticationManager getAuth(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


}
