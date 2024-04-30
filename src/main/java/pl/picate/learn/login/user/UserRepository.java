package pl.picate.learn.login.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long>{
	
	//@Query("SELECT u FROM User u where u.user_name=':user_name'")
	public List<User> findByUserName(String user_name);
	
	@Query("Select u FROM User u where u.email=':email'")
	public Optional<User> findByEmail(String email);
}
