CREATE TABLE user(
	user_email VARCHAR(50) NOT NULL,
	user_type CHAR(10),
	PRIMARY KEY(user_email)
);

CREATE TABLE restaurant(
	restaurant_id VARCHAR(10) NOT NULL,
	restaurant_name VARCHAR(50),
	last_updated DATE,
	PRIMARY KEY(restaurant_id)
);
