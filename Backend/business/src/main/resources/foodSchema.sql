CREATE TABLE food(
	food_id INT NOT NULL,
	food_name VARCHAR(80),
	protein_total INT,
	carb_total INT,
	fat_total INT,
	calorie_total INT,
	located_at INT NOT NULL,
	PRIMARY KEY(food_id),
	FOREIGN KEY(located_at) REFERENCES restaurant(restaurant_id)
);
