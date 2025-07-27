package com.ShowJob.JobProtal.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Security {
	
	

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/register", "/css/**").permitAll() // Allow public access
	                .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	                .loginPage("/login")   // ✅ Your custom login URL
	                .defaultSuccessUrl("/jobs", true)
	                .permitAll()
	            )
	           .logout(logout -> logout.permitAll());
	        
	        return http.build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}


