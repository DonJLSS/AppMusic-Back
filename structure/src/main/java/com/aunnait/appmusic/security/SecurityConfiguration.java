package com.aunnait.appmusic.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

public class SecurityConfiguration{

//    private final JwtTokenFilter jwtTokenFilter;
//
//    public SecurityConfiguration(JwtTokenFilter jwtTokenFilter) {
//        this.jwtTokenFilter = jwtTokenFilter;
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeRequests()
//                .requestMatchers(HttpMethod.GET).permitAll()
//                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")   //Patch treat
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }



//Regular security config
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //Applies before reaching controllers
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST).authenticated()
//                        .requestMatchers(HttpMethod.PATCH).authenticated()
//                        .requestMatchers(HttpMethod.PUT).authenticated()
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
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() { //CORS basic configuration
//        CorsConfiguration cc = new CorsConfiguration();
//        cc.setAllowedOrigins(Arrays.asList("/*"));
//        cc.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE"));
//        cc.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Authorization"));
//        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//        cc.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", cc);
//        return source;
//    }
}
