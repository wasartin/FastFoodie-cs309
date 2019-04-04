CREATE TABLE foodRating(
	rating_id INT NOT NULL AUTO_INCREMENT,
	user_email VARCHAR(50)
	food_id INT,
	PRIMARY KEY(rating_id),
	FOREIGN KEY(user_email) REFERENCES user(user_email),
	FOREIGN KEY(food_id) REFERENCES food(food_id)
);

ALTER TABLE foodRating AUTO_INCREMENT=0;

-- TODO: Trigger for when a new rating is added, then update the food table
