package pl.picate.learn.login.user;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "USER_PICATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	//TODO because its not different then id above. Or some method of add or system of nameing
    private long userID;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String userName;
    private String password;
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public User(long userID,String firstName, String lastName, String email,Date  birth, String userName, String password) {
    	this.userID=userID;
    	this.firstName=firstName;
    	this.lastName=lastName;
    	this.email=email;
    	this.birthDate=birth;
    	this.userName=userName;
    	this.password=password;
    }
    
     
    
}
