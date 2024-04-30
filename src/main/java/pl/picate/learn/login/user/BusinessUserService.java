package pl.picate.learn.login.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.picate.learn.login.token.LoginToken;
import pl.picate.learn.login.token.LoginTokenService;

@Service
public class BusinessUserService implements UserDetailsService{

	
	private final UserService userService;
	
	public BusinessUserService(UserService userService) {
		this.userService=userService;
	}
		
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LoginTokenService loginToken;

    @Autowired
    private UserRepository userRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			List<User> users = userService.getUserRespository().findByUserName(username);
			if(users.size()==0) {
				throw new UsernameNotFoundException("User not found");
			}
		 	User user = users.get(0);

		    BusinessUserDetails customUserDetails = new BusinessUserDetails(user);
		    customUserDetails.setUser(user);

		    return customUserDetails;
	}

	public String signUpUser(User user) {
        boolean userExists = userService.getUserRespository()
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder 
                .encode(user.getPassword());

        user.setPassword(encodedPassword);

        userService.getUserRespository().save(user);

        String token = UUID.randomUUID().toString();

        LoginToken confirmationToken = new LoginToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        loginToken.saveConfirmationToken(
                confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }
	
}
