package com.adkp.fuexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails an = User.builder()
                .username("a123")
                .password("{noop}123")
                .roles("Buyer")
                .build();

        UserDetails dung = User.builder()
                .username("d123")
                .password("{noop}123")
                .roles("Seller")
                .build();

        UserDetails khanh = User.builder()
                .username("k123")
                .password("{noop}123")
                .roles("Moderator")
                .build();

        UserDetails phat = User.builder()
                .username("p123")
                .password("{noop}123")
                .roles("Administrator")
                .build();

        return new InMemoryUserDetailsManager(an, dung, khanh, phat);
    }
}


