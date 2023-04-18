/* This is a Spring Boot service implementation of the UserDetailsService interface. 
It finds a user based on the provided username, retrieves their roles from the repository, 
converts them into a list of granted authorities, and creates a Spring User with the username, 
encrypted password, and granted authorities, returning it as a UserDetails object. 
If the user is not found, it throws a UsernameNotFoundException. */

package ca.sheridancollege.imranfi.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.imranfi.Repositories.HalloweenRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private HalloweenRepository halRepo ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//find user based on user name , if user doesnt exist throw exception. 
		//if it does get list of roles change it into list of granted atheorities 
		//create spring user based on info above
		
		ca.sheridancollege.imranfi.Beans.User user = halRepo.findUserAccount(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User Name not found");
		}
		
		List <String> roles = halRepo.getRolesById(user.getUserId());
		List <GrantedAuthority> grantList = new ArrayList <GrantedAuthority>();
		
		for (String role: roles) {
			grantList.add(new SimpleGrantedAuthority(role));
			
		}
		//Create spring user based on info above
		
		User springUser = new User(user.getUserName(), user.getEncryptedPassword(), grantList);
		
		return (UserDetails)springUser;
		

	}

}
