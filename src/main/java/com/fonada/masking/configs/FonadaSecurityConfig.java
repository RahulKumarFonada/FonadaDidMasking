package com.fonada.masking.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fonada.masking.token.filters.JwtRequestFilter;
import com.fonada.masking.token.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class FonadaSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private static final String[] AUTH_WHITELIST = {
	        "/authenticate",
	        "/swagger-resources/**",
	        "/swagger-ui/**",
	        "/v3/api-docs",
	        "/webjars/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.cors()
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .deny()
        .and()
        .authorizeRequests()
        .antMatchers(AUTH_WHITELIST).permitAll()
        .antMatchers("/auth/token").permitAll()
        .antMatchers("/usercontroller/**").hasRole("Admin")
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
	    return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticateManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
