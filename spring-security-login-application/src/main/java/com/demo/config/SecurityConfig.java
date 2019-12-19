package com.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//*************************************IN Memory****************************************
//		auth
//		.inMemoryAuthentication()
//			.withUser("admin").password("{noop}admin").roles("ADMIN")
//			.and()
//			.withUser("employee").password("{noop}employee").roles("EMPLOYEE")
//			.and()
//			.withUser("manager").password("{noop}manager").roles("MANAGER");
//
//*************************************IN Memory****************************************
		
//*************************************H2 Database**************************************
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.withDefaultSchema()
			// CUSTOM SCHEMA
			// .usersByUsernameQuery("SELECT username, password, enabled "
			//	+ "FROM users"
			//	+ "WHERE usename = ?")
			// .authoritiesByUsernameQuery("SELECT username. authority"
			//	+ "FROM authorities"
			//	+ "WHERE username = ?")
			.withUser(User.withUsername("admin")
					.password("{noop}admin")
					.roles("ADMIN"))
			.withUser(User.withUsername("employee")
					.password("{noop}employee")
					.roles("EMPLOYEE"))
			.withUser(User.withUsername("manager")
					.password("{noop}manager")
					.roles("MANAGER"));
//*************************************H2 Database**************************************
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/").hasAnyRole("ADMIN","EMPLOYEE","MANAGER")
				.antMatchers("/adminStuff").hasRole("ADMIN")
				.antMatchers("/employeeStuff").hasAnyRole("ADMIN","EMPLOYEE")
				.antMatchers("/managerStuff").hasAnyRole("ADMIN","MANAGER")
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/processLogin")
				.permitAll()
			.and()
			.logout()
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
}
