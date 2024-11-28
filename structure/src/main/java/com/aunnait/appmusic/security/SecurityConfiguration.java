package com.aunnait.appmusic.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author jlserrano
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//Regular security config
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //Applies before reaching controllers
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST).authenticated()
//                        .requestMatchers(HttpMethod.GET).hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        return http.build();
//    }

    //Test security config.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //Applies before reaching controllers
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() { //CORS basic configuration
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedOrigins(Arrays.asList("/*"));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        cc.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        cc.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }
}
