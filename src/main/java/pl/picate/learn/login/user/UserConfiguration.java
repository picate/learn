package pl.picate.learn.login.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import jakarta.persistence.EntityManager;
import lombok.Setter;

@Configuration
public class UserConfiguration {
	
	@Autowired
	LocalContainerEntityManagerFactoryBean entityManagerFactory;
   
	@Bean
	EntityManager entityManager() {
		return entityManagerFactory.getObject().createEntityManager(); 
	}
	
	/*@Bean
	@DependsOn({"userRepository","entityManager"})
    UserService userService(@Autowired UserRepository userRespository, @Autowired EntityManager entityManager) {//(@Qualifier("userRepository") UserRepository userRespository, @Qualifier("entityManager") EntityManager entityManager) {
		return new UserService(userRepository,entityManager);
	}*/
	
	@Bean
    public UserService userService(UserRepository userRepository, EntityManager entityManager) {
        UserService userService = new UserService(userRepository,entityManager);
        return userService;
    }
	
	@Bean
	BusinessUserService businessUserService(UserService userService) {
		return new BusinessUserService(userService);
	}
}
