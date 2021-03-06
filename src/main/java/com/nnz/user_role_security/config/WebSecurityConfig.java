package com.nnz.user_role_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnz.user_role_security.service.UserService;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            //url(/admin_page/**) link in controller
            	//.antMatchers("/main/**").hasRole("ADMIN")
            	.antMatchers("/admin_page/**").hasRole("ADMIN")
                .antMatchers("/","/components/**","/static/**","/dist/**","/js/**","/css/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            //url link in controller
                .loginPage("/login_page")
                .loginProcessingUrl("/login_page").usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/main_page").failureUrl("/login_page?error")
                .permitAll()
                .and()
                .csrf();
            
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authProvider());
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCrypePasswordEncoder=new BCryptPasswordEncoder();
		return bCrypePasswordEncoder;
	}
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
