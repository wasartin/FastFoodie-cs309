CREATE TABLE favorites(
	user_id,
	fid,
	PRIMARY KEY(user_id),
	FOREIGN KEY(user_id) REFERENCES user(user_email),
	FOREIGN KEY(fid) REFERENCES food(food_id)
);