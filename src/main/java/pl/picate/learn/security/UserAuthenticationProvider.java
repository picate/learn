package pl.picate.learn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NoArgsConstructor;
import pl.picate.learn.login.user.BusinessUserDetails;
import pl.picate.learn.login.user.BusinessUserService;

@NoArgsConstructor
public class UserAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
    private BusinessUserService userDetailsService;

    
    public UserAuthenticationProvider(BusinessUserService userDetailsServic) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
       userDetails = userDetailsService.loadUserByUsername(authentication.getName());
       super.additionalAuthenticationChecks(userDetails, authentication);
    }
}