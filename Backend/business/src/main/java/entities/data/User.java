package entities.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="USER_EMAIL")
	private String email;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="USER_TYPE")
	private int userType;
	
	
	public User(long user_id, String user_email, String password, int user_type) {
		super();
		this.id = user_id;
		this.email = user_email;
		this.password = password;
		this.userType = user_type;
	}
	public long getId() {
		return id;
	}
	
	public void setId(long user_id) {
		this.id = user_id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String user_email) {
		this.email = user_email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUserType() {
		return userType;
	}
	
	public void setUserType(int user_type) {
		this.userType = user_type;
	}
}