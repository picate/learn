package pl.picate.learn.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAuthenticationManager {
	
	@Autowired
	private final UserAuthenticationProvider daoAuthProvider;
	@Autowired
	private final PasswordEncoder passwordEncoderSecurity;
	
	
	public boolean AuthenticateUser(String userName,String password) {
		daoAuthProvider.setPasswordEncoder(passwordEncoderSecurity);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, password);
		Authentication pass = daoAuthProvider.authenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(pass);
		return pass.isAuthenticated();
	}
	
	
}
