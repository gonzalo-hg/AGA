package mx.uam.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration//Clase de configuracion
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/","/js/**","/imagenes/**","/alumnos/solo").permitAll()
		.antMatchers("/alumnos/matricula/{matricula}").hasAnyRole("ADMIN")
		.antMatchers("/alumnos/solo").hasAnyRole("USER")
		.anyRequest().authenticated();
	}
	//Creamos un componente
	@Bean
	public BCryptPasswordEncoder paswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//Esto es para registrar los usuarios 
	//Lo que hace es encriptar las conttraseñas
	@Autowired //Para el AuthenticacionMB
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		PasswordEncoder encoder =  paswordEncoder();		//Expresion lamda ->
		//Forma en la que se encripta la constraseña
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);//Se obtiene el argumento del encoder y se lo pasa al encode
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("123").roles("ADMIN","USER"))
		.withUser(users.username("user").password("123").roles("USER"));
	}

}
