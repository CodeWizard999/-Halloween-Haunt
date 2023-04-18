// This class SecurityConfig is responsible for configuring the Spring Security settings of the application.

package ca.sheridancollege.imranfi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		////////////////////////////////////////
		
		http
			.authorizeRequests((authz) -> authz 
			//Only users with the roles "VENDER" or "GUEST" can access
				.antMatchers(HttpMethod.GET, "/add").hasAnyRole("VENDER","GUEST") 
				.antMatchers(HttpMethod.GET, "/proccessAdd").hasAnyRole("VENDER","GUEST")

			// Only users with the role "VENDER" can access
				.antMatchers(HttpMethod.GET, "/edit/{id}").hasRole("VENDER") 
				.antMatchers(HttpMethod.GET, "/delete/{id}").hasRole("VENDER") 
				.antMatchers(HttpMethod.GET, "/stats").hasRole("VENDER") 

			// All users can access the root URL ("/")
				.antMatchers(HttpMethod.GET, "/").permitAll() 
				.antMatchers(HttpMethod.GET, "/register").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers("/images/**", "/css/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()

			// Any other request that is not matched by the above patterns requires authentication.
				.anyRequest().authenticated()

		//configuring custom login
				)
			.formLogin()
			.loginPage("/login")
			.permitAll()  
			
		//configuring logout
		.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			
				
		//configuring unauthorized request 
		.and() 
			.exceptionHandling() 
			.accessDeniedHandler(accessDeniedHandler);
		
		return http.build();
	}
	

	
}