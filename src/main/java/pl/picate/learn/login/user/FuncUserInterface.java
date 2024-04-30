package pl.picate.learn.login.user;

import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface FuncUserInterface {
	@Transactional
	@Query("SELECT max(userID) from user")
	public long findByUserID() ;
}
