package com.novi.DemoDrop.config;

import com.novi.DemoDrop.filter.JwtRequestFilter;
import com.novi.DemoDrop.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomment, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers(HttpMethod.GET,"/demos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/demos/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET,"/demos/mydemos/{djId}").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/demos/{demoId}/download").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "demos").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "demos/mp3file").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "demos/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/demos/{id}/reply-to-demo").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/djs").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/djs/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/djs").permitAll()
                .requestMatchers(HttpMethod.GET, "/replies-to-demos/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/replies-to-demos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/replies-to-demos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/replies-to-demos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/talentmanagers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/talentmanagers").permitAll() //permission to make admin account is made in the service layer on basis of email
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasAnyRole("ADMIN", "USER")
                // Je mag meerdere paths tegelijk definieren
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}