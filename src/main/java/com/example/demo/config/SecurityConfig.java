package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
	    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        	    .requestMatchers(HttpMethod.GET, "/api/jobs/**").permitAll()
        	    .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

        	    .requestMatchers(HttpMethod.POST, "/api/jobs")
        	        .hasRole("RECRUITER")

        	    .requestMatchers(HttpMethod.PUT, "/api/jobs/**")
        	        .hasRole("RECRUITER")

        	    .requestMatchers(HttpMethod.DELETE, "/api/jobs/**")
        	        .hasRole("RECRUITER")

        	    .anyRequest().authenticated()
        	)
        	.addFilterBefore(
        	    jwtAuthenticationFilter,
        	    UsernamePasswordAuthenticationFilter.class
        	);

        return http.build();
    }
}