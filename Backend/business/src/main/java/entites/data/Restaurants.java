package entites.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RESTAURANTS")
public class Restaurants {
	
	@Id
	@Column(name="RESTAURANT_ID")
	private long restaurantId;
	@Column(name="RESTAURANT_NAME")
	private String restaurantName;
	@Column(name="LAST_UPDATED")
	private Date lastUpdated;
	
	public Restaurants(long restaurantId, String restaurantName, Date lastUpdated) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.lastUpdated = lastUpdated;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	
}