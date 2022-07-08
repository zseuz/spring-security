package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;

//sirve para el uso de permitir leer a que usuario le sirve cada funcion
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;


	// es nesesario el formLogin y el logout para que identifique el inicio y
	// terminacion de una sesion
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**","/locale","/api/clientes/**").permitAll()
//		.antMatchers("/ver/**").hasAnyRole("USER")
//		.antMatchers("/uploads/**").hasAnyRole("USER")
//		.antMatchers("/form/**").hasAnyRole("ADMIN")
//		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
//		.antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");
		return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();

	}
	@Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
    {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
	
	

//	@Bean
//	public UserDetailsService userDetailsService()throws Exception{
////		PasswordEncoder encoder = passwordEncoder();
////		UserBuilder users = User.builder().passwordEncoder(encoder:: encode);
//		
//		//acceso en duro usuarios
//		
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User
//	            .withUsername("user")
//	            .password(passwordEncoder.encode("user"))
//	            .roles("USER")
//	            .build());
//		 manager.createUser(User
//		            .withUsername("admin")
//		            .password(passwordEncoder.encode("admin"))
//		            .roles("ADMIN","USER")
//		            .build());
//		
//		return manager;
//	}

//	@Bean 
//	public static BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}	
// uso de inicio en duro en jdbc
//	@Bean
//	public static JdbcUserDetailsManager jdbcUserDetailsManager(PasswordEncoder passwordEncoder,
//	        AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
//		JdbcUserDetailsManager jdbcUserDetailsManager = auth.jdbcAuthentication().passwordEncoder(passwordEncoder)
//				.dataSource(dataSource)
//				.usersByUsernameQuery("select username, password, enabled from users where username=?")
//				.authoritiesByUsernameQuery(
//						"select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?")
//				.getUserDetailsService();
//
//		return jdbcUserDetailsManager;
//	}

}
