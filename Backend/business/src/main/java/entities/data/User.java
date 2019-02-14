package entities.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id //specifies that this is a primary key
	@Column(name="user_email")
	private String email;
	@Column(name="user_type")
	private int userType;
	
	public User(String user_email, int user_type) {
		super();
		this.email = user_email;
		this.userType = user_type;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String user_email) {
		this.email = user_email;
	}
	
	public int getUserType() {
		return userType;
	}
	
	public void setUserType(int user_type) {
		this.userType = user_type;
	}
}