package ca.sheridancollege.imranfi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.AntPathRequestMatcherProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

	//does'nt change
	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;
	//authentication
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	
	
	//authorization: overwrite  //this m ethod never changes
	@Bean
	public AuthenticationManager authManger(HttpSecurity http) throws Exception{
		
		
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(encoder)
				.and()
				.build();
	
	}
	
	
	@Bean
	public SecurityFilterChain  filterChain (HttpSecurity http) throws Exception{
		///who is allowed to access what url
		//remove this code before production i.e when not using h2 and using sql
		http.csrf().disable();
		http.headers().frameOptions().disable();
		////////////////////////////////////////
		
		//just need to change antmachers in exam just write this
		http
			.authorizeRequests((authz) -> authz 
				.antMatchers(HttpMethod.GET, "/employee").hasRole("EMPLOYEE") //we can ignore HttpMethod.GET if it is get
				.antMatchers(HttpMethod.GET, "/owner").hasRole("OWNER") 
				.antMatchers(HttpMethod.GET, "/manager").hasRole("MANAGER") 
				.antMatchers(HttpMethod.GET, "/admin").hasAnyRole("EMPLOYEE", "OWNER", "MANAGER")
				.antMatchers(HttpMethod.GET, "/").permitAll() //all can accesss without logging in
				.antMatchers(HttpMethod.GET, "/register").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers("/images/**", "/css/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				//.antMatchers("/**").permitAll() //anything we missed will be caught here
				.anyRequest().authenticated()
		//configure custom login
				)// close lemda  
			.formLogin()
			.loginPage("/login")
			.permitAll()  //no we don't need mapping for login page now 
			
		//configure logout
		.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			
				
		//config unauthorized request 
				
				
				
		.and() 
			.exceptionHandling() 
			.accessDeniedHandler(accessDeniedHandler);
		
		return http.build();
	}
	
	/*
	@Autowired 
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
		//for users and passwords 
		//NoOpPasswordEncoder not encrypted pass
		//role can be anything
		
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
			.withUser("Manager").password("123").roles("MANAGER")
			.and().withUser("Owner").password("123").roles("OWNER")
			.and().withUser("Employee").password("123").roles("EMPLOYEE")
			.and().withUser("Admin").password("123").roles("OWNER", "MANAGER", "EMPLOYEE");
		
		//_csrf tag ensures the info is coming from the person inputting not from random location.
		//it gets embedded in all form actions. it prevents cross-site request frogery.
		//session fixation protection: session id is embedded in http request, i.e u can get
		//someone's session id and embed it into any http request any server will think its them.
		//session fixation protection: prevents people to change their session id. 
	}
	*/
	
	
}