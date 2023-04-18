package ca.sheridancollege.imranfi.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.imranfi.repositories.SecurityRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SecurityRepository secRepo ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		//find user based on user name , if user doesnt exist throw exception. 
		//if it does get list of roles change it into list of granted atheorities 
		//create spring user based on info above
		
		ca.sheridancollege.imranfi.beans.User user = secRepo.findUserAccount(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User Name not found");
		}
		
		List <String> roles = secRepo.getRolesById(user.getUserId());
		List <GrantedAuthority> grantList = new ArrayList <GrantedAuthority>();
		
		for (String role: roles) {
			grantList.add(new SimpleGrantedAuthority(role));
			
		}
		//Create spring user based on info above
		
		User springUser = new User(user.getUserName(), user.getEncryptedPassword(), grantList);
		
		return (UserDetails)springUser;
		

	}

}
