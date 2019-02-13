package entites.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class Users {
	@Id
	@Column(name="USER_ID")
	private long user_id;
	@Column(name="USER_EMAIL")
	private String user_email;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="USER_TYPE")
	private int user_type;
	
	
	public Users(long user_id, String user_email, String password, int user_type) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.password = password;
		this.user_type = user_type;
	}
	public long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_email() {
		return user_email;
	}
	
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUser_type() {
		return user_type;
	}
	
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
}