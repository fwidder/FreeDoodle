package com.github.fwidder.freedoodle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final DataSource dataSource;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.withDefaultSchema()
				.withUser(User.withUsername("user")
						.password(passwordEncoder.encode("pass"))
						.roles("USER"));
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// API Docs
				.antMatchers("/api-docs/**", "/api-docs/", "/api-docs.yaml", "/swagger-ui.html", "/swagger-ui/**").permitAll()
				//API
				.antMatchers("/api/**").authenticated()
				//Everything else
				.anyRequest().fullyAuthenticated()
				// Config
				.and().httpBasic()
				.and().csrf().disable();
	}
}
