package mx.uam.springboot.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * Codificador para el password
	 * */
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	
	/**
	 * Crea Usuasrios en memoria unicamente
	 * */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
        .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
        .and()
        .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
        .and()
        .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
	}

	/**
	 * Restriccion sobre los usuarios para acceder a las distintas
	 * peticiones
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/admin/**").hasRole("ADMIN")
	      .antMatchers("/anonymous*").anonymous()
	      .antMatchers("/login*").permitAll()
	      .anyRequest().authenticated()
	      .and()
	      .formLogin()
	      .loginPage("/login.html")
	      .loginProcessingUrl("/perform_login")
	      .defaultSuccessUrl("/homepage.html", true)
	      .failureUrl("/login.html?error=true")
	      //.failureHandler(authenticationFailureHandler())
	      .and()
	      .logout()
	      .logoutUrl("/perform_logout")
	      .deleteCookies("JSESSIONID");
	      //.logoutSuccessHandler(logoutSuccessHandler());
	}

	
}
