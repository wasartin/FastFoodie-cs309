CREATE TABLE users(
	user_email VARCHAR(50),
	user_type VARCHAR(10),
	PRIMARY KEY(user_email)
);

CREATE TABLE foods(
	food_id INT,
	food_name VARCHAR(50),
	protein_total INT,
	carb_total INT,
	fat_total INT,
	calories_total INT,
	weight_oz_total INT,
	price FLOAT,
	located_at_restaurant_id INT,
	PRIMARY KEY(food_id),
	FOREIGN KEY(located_at_restaurant_id) REFERENCES restaurant(restaurant_id)
);

CREATE TABLE restaurants(
	restaurant_id INT NOT NULL,
	restaurant_name VARCHAR(50),
	last_updated DATE,
	PRIMARY KEY(restaurant_id)
);

CREATE TABLE favorited(
	user_id_fk INT,
	food_id_fk INT,
	PRIMARY KEY (user_id_fk) REFERENCES users (user_id),
	FOREIGN KEY (user_id_fk) REFERENCES users (user_id),
	FOREIGN KEY (food_id_fk) REFERENCES foods (food_id)
);
