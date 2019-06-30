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
	password             VARCHAR(20) NOT NULL,
	email                VARCHAR(50) unique NOT NULL,
	name                 VARCHAR(20),
	surname              VARCHAR(20),
	PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
	role                 VARCHAR(20) NOT NULL,
	id                   INTEGER NOT NULL,
	PRIMARY KEY (id,role),
	FOREIGN KEY (id) REFERENCES Users (id)
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
	number               INTEGER NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Carriages
(
	num_carriage         INTEGER NOT NULL,
	id_type              INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (num_carriage,id_train),
	FOREIGN KEY (id_type) REFERENCES Types (id),
	FOREIGN KEY (id_train) REFERENCES Trains (id)
);

CREATE TABLE Seats
(
	num_carriage         INTEGER NOT NULL,
	num_seat             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (num_carriage,num_seat,id_train),
	FOREIGN KEY (num_carriage, id_train) REFERENCES Carriages (num_carriage, id_train)
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
	FOREIGN KEY (id_city) REFERENCES Cities (id)
);

CREATE TABLE Routes
(
	id                   INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (id,id_train),
	FOREIGN KEY (id_train) REFERENCES Trains (id)
);

CREATE TABLE routes_station
(
	id_station           INTEGER NOT NULL,
	time_start           DATETIME NOT NULL,
	time_end             DATETIME NULL,
	price                INTEGER NULL,
	id_route             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (id_station,id_route,id_train),
	FOREIGN KEY (id_station) REFERENCES Stations (id),
	FOREIGN KEY (id_route, id_train) REFERENCES Routes (id, id_train)
);

CREATE TABLE routes_on_date
(
	date_start           DATETIME NOT NULL,
	id_station           INTEGER NOT NULL,
	id_route             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	time_date_start      DATETIME NULL,
	time_date_end        DATETIME NULL,
	PRIMARY KEY (date_start,id_station,id_route,id_train),
	FOREIGN KEY (id_station, id_route, id_train) REFERENCES routes_station (id_station, id_route, id_train)
);

CREATE TABLE user_check
(
	id_user              INTEGER NOT NULL,
	total_price          INTEGER NULL,
	id_train             INTEGER NOT NULL,
	num_carriage         INTEGER NOT NULL,
	num_seat             INTEGER NOT NULL,
	date_start           DATETIME NOT NULL,
	id_station           INTEGER NOT NULL,
	id_route             INTEGER NOT NULL,
	date_start_station   DATETIME NOT NULL,
	date_end_station     DATETIME NULL,
	PRIMARY KEY (id_user,date_start_station,id_train,num_carriage,num_seat,id_station,id_route),
	FOREIGN KEY (id_user) REFERENCES Users (id),
	FOREIGN KEY (num_carriage, num_seat, id_train) REFERENCES Seats (num_carriage, num_seat, id_train),
	FOREIGN KEY (date_start, id_station, id_route, id_train) REFERENCES routes_on_date (date_start, id_station, id_route, id_train)
);

INSERT INTO users (login,password,email) VALUES('admin','admin','admin@mail.com');
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

INSERT INTO carriages(num_carriage, id_train, id_type) VALUES(1,1,1);
INSERT INTO carriages(num_carriage, id_train, id_type) VALUES(2,1,1);
INSERT INTO carriages(num_carriage, id_train, id_type) VALUES(3,1,1);

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
INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) VALUES(1,1,3, '2019-7-19 5:57:00', '2019-8-19 6:05:00',30);
INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) VALUES(1,1,4, '2019-7-19 8:22:00', '2019-8-19 8:22:00',30);

INSERT INTO routes_on_date(date_start, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-18 17:34:00',1,1,1, '2019-9-18 17:34:00', '2019-9-18 17:34:00');
INSERT INTO routes_on_date(date_start, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-18 17:56:00',1,1,2, '2019-9-18 17:50:00', '2019-9-18 17:34:00');
INSERT INTO routes_on_date(date_start, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-19 6:05:00',1,1,3, '2019-9-19 5:57:00', '2019-9-19 6:05:00');
INSERT INTO routes_on_date(date_start, id_train, id_route, id_station, time_date_start, time_date_end) 
VALUES('2019-9-19 8:22:00',1,1,4, '2019-9-19 8:22:00', '2019-9-19 8:22:00');
