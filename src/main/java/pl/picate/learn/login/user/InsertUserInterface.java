package pl.picate.learn.login.user;

import jakarta.transaction.Transactional;

public interface InsertUserInterface {

	@Transactional
	public void insertWithQuery(User user);
}
