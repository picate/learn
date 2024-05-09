package pl.picate.learn.security.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import lombok.Getter;

@Controller
@Getter
public class UserAuthenticationManager implements AuthenticationManager{
	
	private final List<AuthenticationProvider> authProviderList; 
	
	public UserAuthenticationManager(List<AuthenticationProvider> authProvider) {
		this.authProviderList = authProvider;
	}

	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = null;
        
        // Iterate through all registered authentication providers until one successfully authenticates the request
        for (AuthenticationProvider provider : authProviderList) {
            if (provider.supports(authentication.getClass())) {
                try {
                    result = provider.authenticate(authentication);
                    if (result != null) {
                        break; // Authentication successful
                    }
                } catch (AuthenticationException e) {
                    // Log or handle the authentication failure
                }
            }
        }
        
        if (result == null) {
            throw new AuthenticationException("Authentication failed") {}; // All providers failed
        }
        
        return result;
    }
}
