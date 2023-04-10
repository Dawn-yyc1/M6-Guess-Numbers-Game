DROP DATABASE IF EXISTS GuessingNumbersGameTest;
CREATE DATABASE GuessingNumbersGameTest;

USE GuessingNumbersGameTest; 

CREATE TABLE Game (
	GameId INT PRIMARY KEY AUTO_INCREMENT,
    Answer CHAR(4) NOT NULL, 
    RoundStatus BOOL DEFAULT false NOT NULL
);

CREATE TABLE Round(
	RoundId INT PRIMARY KEY AUTO_INCREMENT, 
    GameId INT NOT NULL,
	Guess CHAR(4) NOT NULL,
    Result VARCHAR(15) NOT NULL,
    GameTime DATETIME NOT NULL
);

ALTER TABLE Round
	ADD CONSTRAINT fk_Round_Game
    FOREIGN KEY fk_Round_Game(GameId)
		REFERENCES Game(GameId);

