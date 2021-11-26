package com.lino4000.petFinder.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();  
		
		encoders.put("argon2", new Argon2PasswordEncoder());

		return new DelegatingPasswordEncoder("argon2", encoders);
	}
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	if (!registry.hasMappingForPattern("/img/**")) {
    		registry.addResourceHandler("/img/**").addResourceLocations(
    				"classpath:/static/img/");
    	}
    	if (!registry.hasMappingForPattern("/theme/**")) {
    		registry.addResourceHandler("/theme/**").addResourceLocations(
    				"classpath:/static/theme/");
    	}
    	if (!registry.hasMappingForPattern("/js/**")) {
    		registry.addResourceHandler("/js/**").addResourceLocations(
    				"classpath:/static/js/");
    	}
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
	        .ignoring()
	        .antMatchers(
	        		"/console/**",
	        		"/img/**",
	        		"/js/**",
	        		"/theme/style.css",
	        		"/theme/favicon.png"
	        		);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
		        .csrf()
		        .ignoringAntMatchers("/nocsrf","/device/**", "/logout")
	        .and()
	        	.logout()
	        	.logoutUrl("/logout")
	        	.logoutSuccessUrl("/login")
	        	.invalidateHttpSession(true)
	        	.deleteCookies("JSESSIONID")
            .and()
            	.formLogin()
            	.loginPage("/login")
            	.loginProcessingUrl("/auth").permitAll()
            	.defaultSuccessUrl("/dashboard/welcome")
            .and()
		        .authorizeRequests()
		        .antMatchers("/register").permitAll()
		        .antMatchers("/view/*").permitAll()
		        .antMatchers(HttpMethod.GET,"/device/**").permitAll()
		        .antMatchers(HttpMethod.POST,"/device/**").permitAll()
		        .antMatchers(HttpMethod.PUT,"/device/**").permitAll()
	            .anyRequest().authenticated();
    }
}
