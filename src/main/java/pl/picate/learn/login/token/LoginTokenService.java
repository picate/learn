package pl.picate.learn.login.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Configuration
public class LoginTokenService {
	
	@Autowired
    LoginTokenRepository loginTokenRepository;
   
	
	@Bean
	LoginTokenService loginToken() {
		return new LoginTokenService();
	}
	
    public void saveConfirmationToken(LoginToken token) {
    	loginTokenRepository.save(token);
    }

    public Optional<LoginToken> getToken(String token) {
        return loginTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return loginTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
