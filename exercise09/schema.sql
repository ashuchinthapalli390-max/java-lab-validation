-- Use only a disposable lab database.
CREATE DATABASE IF NOT EXISTS mydatabase;
USE mydatabase;
CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT,username VARCHAR(100) NOT NULL,email VARCHAR(255) NOT NULL UNIQUE);
INSERT INTO users(username,email) VALUES ('johndoe','johndoe@example.com'),('janedoe','janedoe@example.com'),('alice','alice@example.com');
