DROP DATABASE IF EXISTS db_train;
CREATE DATABASE IF NOT EXISTS db_train
default charset utf8
COLLATE utf8_general_ci;

	
-- CREATE USER IF NOT EXISTS 'jusertest'@'localhost' IDENTIFIED BY '12345';
-- GRANT ALL  PRIVILEGES ON db_train.* TO 'jusertest'@'localhost';
USE db_train;
DROP TABLE if exists user_check;
DROP TABLE if exists user_roles;
DROP TABLE if exists Users;
drop table if exists routes_on_date;
drop table if exists routes_station;
drop table if exists Stations;
drop table if exists Cities;
drop table if exists Countries;
drop table if exists Routes;
drop table if exists Seats;
drop table if exists Carriages;
drop table if exists Types;
drop table if exists Trains;

CREATE TABLE Users
(
	id                   INTEGER NOT NULL auto_increment,
	login                VARCHAR(20) unique NOT NULL,
	password             VARCHAR(512) NOT NULL,
	email                VARCHAR(50) NOT NULL,
	name                 VARCHAR(20),
	surname              VARCHAR(20),
    pin_code			VARCHAR(512),
	PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
	role                 VARCHAR(20) NOT NULL,
	id                   INTEGER NOT NULL,
	PRIMARY KEY (id,role),
	FOREIGN KEY (id) REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE Types
(
	id                   INTEGER NOT NULL auto_increment,
	name                 VARCHAR(20) NULL,
	price                INTEGER NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Trains
(
	id                   INTEGER NOT NULL auto_increment,
	number               INTEGER unique NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Carriages
(
	num_carriage         INTEGER NOT NULL,
	id_type              INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
    max_size 			 INTEGER NOT NULL,
	PRIMARY KEY (num_carriage,id_train),
	FOREIGN KEY (id_type) REFERENCES Types (id) ON DELETE CASCADE,
	FOREIGN KEY (id_train) REFERENCES Trains (id) ON DELETE CASCADE
);

CREATE TABLE Seats
(
	num_carriage         INTEGER NOT NULL,
	num_seat             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (num_carriage,num_seat,id_train),
	FOREIGN KEY (num_carriage, id_train) REFERENCES Carriages (num_carriage, id_train) ON DELETE CASCADE
);

CREATE TABLE Countries
(
	id                   Integer NOT NULL auto_increment,
	name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Cities
(
	id_country           Integer(20) NOT NULL,
	id                   INTEGER NOT NULL auto_increment,
    name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (id_country) REFERENCES Countries (id) 
);

CREATE TABLE Stations
(
	id_city              INTEGER NULL,
	name                 VARCHAR(20) NOT NULL,
	id                   INTEGER NOT NULL auto_increment,
	PRIMARY KEY (id),
	FOREIGN KEY (id_city) REFERENCES Cities (id)  ON DELETE CASCADE
);

CREATE TABLE Routes
(
	id                   INTEGER NOT NULL,
	id_train             INTEGER unique NOT NULL,
	PRIMARY KEY (id,id_train),
	FOREIGN KEY (id_train) REFERENCES Trains (id)  ON DELETE CASCADE
);

CREATE TABLE routes_station
(
	id_station           INTEGER NOT NULL,
	time_start           DATETIME NOT NULL,
	time_end             DATETIME NOT NULL,
	price                INTEGER NULL,
	id_route             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (id_station,id_route,id_train),
	FOREIGN KEY (id_station) REFERENCES Stations (id)  ON DELETE CASCADE,
	FOREIGN KEY (id_route, id_train) REFERENCES Routes (id, id_train)  ON DELETE CASCADE
);

CREATE TABLE routes_on_date
(
	date_end             DATETIME NOT NULL,
	id_station           INTEGER NOT NULL,
	id_route             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	time_date_start      DATETIME NULL,
	time_date_end        DATETIME NULL,
	PRIMARY KEY (date_end,id_station,id_route,id_train),
	FOREIGN KEY (id_station, id_route, id_train) REFERENCES routes_station (id_station, id_route, id_train)  ON DELETE CASCADE
);

CREATE TABLE user_check
(
	id_user              INTEGER NOT NULL,
	total_price          INTEGER NULL,
	id_train             INTEGER NOT NULL,
	num_carriage         INTEGER NOT NULL,
	num_seat             INTEGER NOT NULL,
	date_end           DATETIME NOT NULL,
	id_station           INTEGER NOT NULL,
	id_route             INTEGER NOT NULL,
    initials			 VARCHAR(100),
	date_start_station   DATETIME,
	date_end_station     DATETIME,
	PRIMARY KEY (id_user,date_end,id_train,num_carriage,num_seat,id_station,id_route),
	FOREIGN KEY (id_user) REFERENCES Users (id) ON DELETE CASCADE,
	FOREIGN KEY (num_carriage, num_seat, id_train) REFERENCES Seats (num_carriage, num_seat, id_train) ON DELETE CASCADE,
	FOREIGN KEY (date_end, id_station, id_route, id_train) REFERENCES routes_on_date (date_end, id_station, id_route, id_train) ON DELETE CASCADE
);

INSERT INTO users (login,password,email, name, surname) VALUES('admin','21232F297A57A5A743894A0E4A801FC3','admin@mail.com','AdminName','AdminSurname');
INSERT INTO user_roles(id, role) VALUES (1,'USER');
INSERT INTO user_roles(id, role) VALUES (1,'ADMIN');

INSERT INTO countries (name) values ('Ukraine');
INSERT INTO cities(id_country, name) values (1, 'Odessa');
INSERT INTO cities(id_country, name) values (1, 'Poltava');
INSERT INTO cities(id_country, name) values (1, 'Kharkov');
INSERT INTO cities(id_country, name) values (1, 'Kyiv');
INSERT INTO cities(id_country, name) values (1, 'Lviv');

-- Adding Station test info --  
INSERT INTO Stations(id_city,name) VALUES(1, 'Odessa-Holovna');
INSERT INTO Stations(id_city,name) VALUES(1, 'Odessa-Mala');
INSERT INTO Stations(id_city,name) VALUES(2, 'Poltava-Pivdenna');
INSERT INTO Stations(id_city,name) VALUES(3, 'Kharkiv');

-- Adding Train test info --  
INSERT INTO trains(number) values(263);

INSERT INTO types(name, price) values('common', 100);
INSERT INTO types(name, price) values('coupe', 250);

INSERT INTO carriages(num_carriage, id_train, id_type, max_size) VALUES(1,1,1,45);
INSERT INTO carriages(num_carriage, id_train, id_type, max_size) VALUES(2,1,2,45);
INSERT INTO carriages(num_carriage, id_train, id_type, max_size) VALUES(3,1,1,45);

INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,1,1);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,1,2);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,1,3);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,1,4);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,1,5);

INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,2,1);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,2,2);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,2,3);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,2,4);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,2,5);

INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,3,1);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,3,2);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,3,3);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,3,4);
INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(1,3,5);

-- Adding Route test info --  
INSERT INTO routes(id, id_train) VALUES(1,1);

INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) VALUES(1,1,1, '2019-7-18 17:34:00', '2019-7-18 17:34:00', 20);
INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) VALUES(1,1,2, '2019-7-18 17:50:00', '2019-7-18 17:56:00', 70);
INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) VALUES(1,1,3, '2019-7-19 5:57:00', '2019-7-19 6:05:00',30);
INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) VALUES(1,1,4, '2019-7-19 8:22:00', '2019-7-19 8:22:00',30);

INSERT INTO routes_on_date(date_end, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-18 17:34:00',1,1,1, '2019-9-18 17:34:00', '2019-9-18 17:34:00');
INSERT INTO routes_on_date(date_end, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-18 17:56:00',1,1,2, '2019-9-18 17:50:00', '2019-9-18 17:56:00');
INSERT INTO routes_on_date(date_end, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-19 6:05:00',1,1,3, '2019-9-19 5:57:00', '2019-9-19 6:05:00');
INSERT INTO routes_on_date(date_end, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-19 8:22:00',1,1,4, '2019-9-19 8:22:00', '2019-9-19 8:22:00');


-- TEST #1 Somebody buy ticket simulation--
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1, '2019-9-18 17:34:00' ,1,2,1,1,1);
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1,'2019-9-18 17:56:00' ,1,2,1,2,1);
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1,'2019-9-19 6:05:00',1,2,1,3,1);  

-- TEST #2 Somebody buy ticket simulation--
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1, '2019-9-18 17:34:00' ,1,1,3,1,1);
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1,'2019-9-18 17:56:00' ,1,1,3,2,1);
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1,'2019-9-19 6:05:00'  ,1,1,3,3,1);  	 

-- TEST #3 Somebody buy ticket simulation--
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1, '2019-9-18 17:34:00' ,1,1,2,1,1);
INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route) 
VALUES(1,'2019-9-18 17:56:00' ,1,1,2,2,1);
