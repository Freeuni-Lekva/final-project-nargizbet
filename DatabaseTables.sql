USE nargizbet;
 
DROP TABLE IF EXISTS balances;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS blackjackGame;
DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS friend_requests;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	username VARCHAR(64) PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    profile_picture LONGBLOB,
    member_since DATE NOT NULL,
    psw VARCHAR(256) NOT NULL
);

CREATE TABLE balances (
	username VARCHAR(64),
    balance DOUBLE,
    FOREIGN KEY (username) REFERENCES users(username),
    UNIQUE (username)
);

CREATE TABLE friends (
	username1 VARCHAR(64),
    username2 VARCHAR(64),
    FOREIGN KEY(username1) REFERENCES users(username),
    FOREIGN KEY(username1) REFERENCES users(username)
);

CREATE TABLE friend_requests (
	username1 VARCHAR(64),
    username2 VARCHAR(64),
    FOREIGN KEY(username1) REFERENCES users(username),
    FOREIGN KEY(username1) REFERENCES users(username)
);

CREATE TABLE blackjackGame (
	username VARCHAR(64),
    wins INT NOT NULL,
    FOREIGN KEY(username) REFERENCES users(username),
    UNIQUE (username)
);

CREATE TABLE slots (
	username VARCHAR(64),
    wins DOUBLE NOT NULL,
    FOREIGN KEY(username) REFERENCES users(username),
    UNIQUE (username)
);
