package entities.data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FOODS")
public class Food {
	@Id
	@Column(name="FOOD_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="PROTEIN_TOTAL")
	private int protein;
	@Column(name="CARB_TOTAL")
	private int carb;
	@Column(name="FAT_TOTAL")
	private int fat;
	@Column(name="CALORIES_TOTAL")
	private int calories_total;//"
	@Column(name="WEIGHT_OZ_TOTAL")
	private int weight_oz_total;
	@Column(name="LOCATED_AT_RESTAURANT_ID")
	private int located_at_restaurant_id;
	
	public Food() {
		super();
	}
	
	public Food(long id, String name, int protein, int carb, int fat, int calories,
			int weightInOz, int located_at_restaurant_id) {
		super();
		this.id = id;
		this.name = name;
		this.protein = protein;
		this.carb = carb;
		this.fat = fat;
		this.calories_total = calories;
		this.weight_oz_total = weightInOz;
		this.located_at_restaurant_id = located_at_restaurant_id;
	}


	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}

	public int getWeightInOz() {
		return weight_oz_total;
	}

	public void setWeightInOz(int weightInOz) {
		this.weight_oz_total = weightInOz;
	}

	public int getRestaurantLocatedAt() {
		return located_at_restaurant_id;
	}

	public void setRestaurantLocatedAt(int located_at_restaurant_id) {
		this.located_at_restaurant_id = located_at_restaurant_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public int getCalories() {
		return calories_total;
	}

	public void setCalories(int calories) {
		this.calories_total = calories;
	}
}