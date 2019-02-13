package entities.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RESTAURANTS")
public class Restaurant {
	
	@Id
	@Column(name="RESTAURANT_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="RESTAURANT_NAME")
	private String name;
	@Column(name="LAST_UPDATED")
	private Date lastUpdated;
	
	public Restaurant(long restaurantId, String restaurantName, Date lastUpdated) {
		super();
		this.id = restaurantId;
		this.name = restaurantName;
		this.lastUpdated = lastUpdated;
	}

	public long getId() {
		return id;
	}

	public void setId(long restaurantId) {
		this.id = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String restaurantName) {
		this.name = restaurantName;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}