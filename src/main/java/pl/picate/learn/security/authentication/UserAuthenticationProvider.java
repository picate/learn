package pl.picate.learn.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.picate.learn.login.user.BusinessUserService;

public class UserAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
    private BusinessUserService businessUserService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoderSecurity;
    
    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        UserDetails userDetails = businessUserService.loadUserByUsername(username);
        
        if(username.equals(userDetails.getUsername()) && passwordEncoderSecurity.matches(password,userDetails.getPassword()))
        	return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		return super.authenticate(authentication);
	}
}