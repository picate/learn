package pl.picate.learn.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import pl.picate.learn.login.user.BusinessUserService;

public class UserAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
    private BusinessUserService userDetailsService;

    private UsernamePasswordAuthenticationToken authenticationToken;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
       userDetails = userDetailsService.loadUserByUsername(authentication.getName());
       super.additionalAuthenticationChecks(userDetails, authentication);
    }
}