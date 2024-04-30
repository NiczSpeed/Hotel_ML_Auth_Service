package com.ml.hotel_ml_auth_service.configuration;

import com.ml.hotel_ml_auth_service.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("auth/register").permitAll()
                        .requestMatchers("auth/test").permitAll() //kafka jest tu podloczona zamienie to pozniej z auth/register
                        .requestMatchers("auth/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())

//                .httpBasic(Customizer.withDefaults()).formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll()
//                )

//                .httpBasic(Customizer.withDefaults()).logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .addLogoutHandler(new SecurityContextLogoutHandler()))



                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

}

