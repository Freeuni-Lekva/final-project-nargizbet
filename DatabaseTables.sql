USE nargizbet;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	username VARCHAR(64) PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    profile_picture BLOB,
    member_since DATE NOT NULL,
    password VARCHAR(256) NOT NULL
);
 
DROP TABLE IF EXISTS balance;
CREATE TABLE balance (
	username VARCHAR(64),
    balance DOUBLE,
    FOREIGN KEY(username) REFERENCES users(username)
);

DROP TABLE IF EXISTS balance;
CREATE TABLE balance (
	username VARCHAR(64),
    balance DOUBLE,
    FOREIGN KEY(username) REFERENCES users(username)
);

DROP TABLE IF EXISTS balance;
CREATE TABLE balance (
	username1 VARCHAR(64),
    username2 VARCHAR(64),
    FOREIGN KEY(username1) REFERENCES users(username),
    FOREIGN KEY(username1) REFERENCES users(username)
);

DROP TABLE IF EXISTS blackjack;
CREATE TABLE blackjack (
	username VARCHAR(64),
    wins INT,
    FOREIGN KEY(username) REFERENCES users(username)
);

DROP TABLE IF EXISTS slots;
CREATE TABLE slots (
	username VARCHAR(64),
    money_won DOUBLE,
    FOREIGN KEY(username) REFERENCES users(username)
);
