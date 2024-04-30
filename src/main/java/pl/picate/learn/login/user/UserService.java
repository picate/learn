package pl.picate.learn.login.user;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Query;
import lombok.Getter;
import lombok.Setter;

@Service
@Configurable
@Getter
@Setter
@NamedQuery(name = "get_max_user_id_of_users", query = "SELECT MAX(USER_ID) KEEP (DENSE_RANK FIRST ORDER BY USER_ID DESC) from USERS")
public class UserService implements InsertUserInterface, FuncUserInterface {
	
	private final UserRepository userRespository;
	private final EntityManager entityManager;

    public UserService(UserRepository userRespository, EntityManager entityManager) {
        this.userRespository = userRespository;
        this.entityManager = entityManager;
    }
	
	@Override
	public void insertWithQuery(User user) {
		entityManager.createNativeQuery(
				"INSERT INTO user (userID,firstName,lastName,email,birthDate,userName,password) VALUES (?,?,?,?,?,?,?)")
				.setParameter(1, user.getUserID()).setParameter(2, user.getFirstName())
				.setParameter(3, user.getLastName()).setParameter(4, user.getEmail())
				.setParameter(5, user.getBirthDate()).setParameter(6, user.getUserName())
				.setParameter(7, user.getPassword()).executeUpdate();

	}

	@Override
	public long findByUserID() {
		Query query = entityManager
				.createNativeQuery("SELECT MAX(USER_ID) as \"MAX\" from USERS");
		if(query.getFirstResult()==0)
			return 0;
		return Long.valueOf(query.getParameterValue("MAX").toString());
	}

}
