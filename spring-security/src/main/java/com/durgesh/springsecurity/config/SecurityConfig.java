package com.durgesh.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public UserDetailsService userDetails() {
		UserDetails normal = User
				.builder()
				.username("sourav")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		
		UserDetails admin = User
				.withUsername("Sourav1")
				.password(passwordEncoder().encode("password1"))
				.roles("ADMIN")
				.build();
				
		return new InMemoryUserDetailsManager(normal,admin);
	}
	
	
	// this is for configuring the security and authorization
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.requestMatchers("/api/v1/greetings/normal")
			.hasRole("USER")
			.requestMatchers("/api/v1/greetings/admin")
			.hasRole("ADMIN")
			.requestMatchers("/api/v1/greetings")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin();
		return http.build();
			
	}
	

}
