CREATE TABLE food(
	food_id INT NOT NULL AUTO_INCREMENT,
	food_name VARCHAR(80),
	protein_total INT,
	carb_total INT,
	fat_total INT,
	calorie_total INT,
	price VARCHAR(20),
	category varchar(80),
	located_at INT NOT NULL,
	PRIMARY KEY(food_id),
	FOREIGN KEY(located_at) REFERENCES restaurant(restaurant_id)
);

ALTER TABLE food AUTO_INCREMENT=0;
