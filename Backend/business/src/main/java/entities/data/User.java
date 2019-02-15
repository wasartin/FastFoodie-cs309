package entities.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id //specifies that this is a primary key
	@Column(name="user_email")
	private String user_email;
	@Column(name="user_type")
	private int user_type;
	
	public User(String user_email, int user_type) {
		super();
		this.user_email = user_email;
		this.user_type = user_type;
	}
	
	public String getEmail() {
		return user_email;
	}
	
	public void setEmail(String user_email) {
		this.user_email = user_email;
	}
	
	public int getUserType() {
		return user_type;
	}
	
	public void setUserType(int user_type) {
		this.user_type = user_type;
	}
}