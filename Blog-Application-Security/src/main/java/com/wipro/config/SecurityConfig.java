package com.wipro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wipro.security.JwtAuthenticationEntryPoint;
import com.wipro.security.JwtAuthenticationFilter;



@Configuration
public class SecurityConfig {
	@Autowired
	private JwtAuthenticationEntryPoint point;
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;



	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.disable())
	            .authorizeHttpRequests(auth -> auth
	            		.requestMatchers("/auth/register", "/auth/login").permitAll()
	            		.requestMatchers("/public/**").permitAll() 
	                    .requestMatchers("/api/blogs").hasAnyRole("USER", "ADMIN") 
	                    .requestMatchers("/api/blogs/comment").hasAnyRole("USER", "ADMIN") 
	                .requestMatchers("/api/blogs/{id}").hasAnyRole("USER", "ADMIN") 
	                .requestMatchers("/api/blogs/update/{id}").hasAnyRole("USER", "ADMIN") 
	                .requestMatchers("/api/blogs/comment").hasAnyRole("USER", "ADMIN") 
	                .requestMatchers("/api/blogs/**").hasRole("ADMIN") 
	                .anyRequest().authenticated())  
	            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
	 
	 @Bean
	 public DaoAuthenticationProvider doDaoAuthenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		 return daoAuthenticationProvider;
	 }

}