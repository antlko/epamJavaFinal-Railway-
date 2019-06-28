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
	login                INTEGER unique NOT NULL,
	password             VARCHAR(20) NOT NULL,
	email                VARCHAR(50) unique NOT NULL,
	name                 VARCHAR(20) NOT NULL,
	surname              VARCHAR(20) NOT NULL,
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
	time_start           DATE NOT NULL,
	time_end             DATE NULL,
	price                INTEGER NULL,
	id_route             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	PRIMARY KEY (id_station,id_route,id_train),
	FOREIGN KEY (id_station) REFERENCES Stations (id),
	FOREIGN KEY (id_route, id_train) REFERENCES Routes (id, id_train)
);

CREATE TABLE routes_on_date
(
	date_start           DATE NOT NULL,
	id_station           INTEGER NOT NULL,
	id_route             INTEGER NOT NULL,
	id_train             INTEGER NOT NULL,
	time_date_start      DATE NULL,
	time_date_end        DATE NULL,
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
	date_start           DATE NOT NULL,
	id_station           INTEGER NOT NULL,
	id_route             INTEGER NOT NULL,
	date_start_station   DATE NOT NULL,
	date_end_station     DATE NULL,
	PRIMARY KEY (id_user,date_start_station,id_train,num_carriage,num_seat,id_station,id_route),
	FOREIGN KEY (id_user) REFERENCES Users (id),
	FOREIGN KEY (num_carriage, num_seat, id_train) REFERENCES Seats (num_carriage, num_seat, id_train),
	FOREIGN KEY (date_start, id_station, id_route, id_train) REFERENCES routes_on_date (date_start, id_station, id_route, id_train)
);
