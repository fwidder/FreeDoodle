package com.github.fwidder.freedoodle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true )
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final DataSource dataSource;
	private final PasswordEncoder passwordEncoder;
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	@Autowired
	public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Bean
	public JdbcUserDetailsManager userDetailsManager() {
		return jdbcUserDetailsManager;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		jdbcUserDetailsManager = auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder)
				.getUserDetailsService();
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// Main Page
				.antMatchers("/").permitAll()
				// API Docs
				.antMatchers("/api-docs/**", "/api-docs/", "/api-docs.yaml", "/swagger-ui.html", "/swagger-ui/**").permitAll()
				// H2
				.antMatchers("/h2-console/**", "/h2-console/").permitAll()
				// API
				.antMatchers("/api/**").authenticated()
				// Web
				.antMatchers("/web/public/**").permitAll()
				.antMatchers("/web/private/**").authenticated()
				// Web Jars
				.antMatchers("/webjars/**").permitAll()
				// Everything else
				.anyRequest().authenticated()
				// Login
				.and().formLogin()
				.loginPage("/web/public/login").permitAll()
				.failureUrl("/web/public/login?loginError=true")
				.defaultSuccessUrl("/web/private/profile?login=true", true)
				// Logout
				.and().logout()
				.logoutUrl("/web/public/logout").permitAll()
				.logoutSuccessUrl("/web/public/index?logout=true")
				// HTTP Auth
				.and().httpBasic()
				// Other
				.and().csrf().disable()
				.headers().frameOptions().disable();
	}
}
