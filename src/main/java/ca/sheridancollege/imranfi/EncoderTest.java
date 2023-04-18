// This is a Java class that demonstrates how to use the BCryptPasswordEncoder class from Spring Security to encrypt a password.

package ca.sheridancollege.imranfi;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderTest {
	
	public static String encryptPassword(String password){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(password);
		
		
	}
	public static void main(String[] agrs) {
		String pass = "123";
		String encodedpas = encryptPassword(pass);
		System.out.print(encodedpas);
		
	}
}
