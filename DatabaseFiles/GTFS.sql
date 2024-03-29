Create DATABASE IF NOT EXISTS gtfs Default character set utf8 default collate utf8_general_ci;

USE gtfs;

DROP TABLE IF EXISTS agency;

create table agency (
	agency_id varchar(255) NOT NULL PRIMARY KEY,
	agency_name varchar(255),
	agency_url varchar(255),
	agency_timezone varchar(255),
	agency_lang varchar(255),
	agency_phone varchar(255)
	);

LOAD DATA LOCAL INFILE "agency.txt" INTO TABLE agency FIELDS TERMINATED BY "," ENCLOSED BY '"' IGNORE 1 LINES;

DROP TABLE IF EXISTS calendar;

create table calendar (
	service_id varchar(255) NOT NULL PRIMARY KEY,
	monday TINYINT(1),
	tuesday TINYINT(1),
	wednesday TINYINT(1),
	thursday TINYINT(1),
	friday TINYINT(1),
	saturday TINYINT(1),
	sunday TINYINT(1),
	start_date DATE,
	end_date DATE
);

LOAD DATA LOCAL INFILE "calendar.txt" INTO TABLE calendar FIELDS TERMINATED BY ","
IGNORE 1 LINES
(service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday, @var1,@var2)
SET start_date = STR_TO_DATE(@var1,'%Y%m%d') , end_date = STR_TO_DATE(@var2,'%Y%m%d');


DROP TABLE IF EXISTS calendar_dates;

create table calendar_dates (
	service_id varchar(255) NOT NULL,
	dateC DATE,
	exception_type TINYINT(1),
	PRIMARY KEY(service_id,dateC)
);

LOAD DATA LOCAL INFILE "calendar_dates.txt" INTO TABLE calendar_dates FIELDS TERMINATED BY ","
IGNORE 1 LINES
(service_id, @var1, exception_type)
SET dateC = STR_TO_DATE(@var1,'%Y%m%d');


DROP TABLE IF EXISTS routes;

create table routes(
	route_id varchar(255) NOT NULL PRIMARY KEY,
	agency_id varchar(255),
	route_short_name varchar(255),
	route_long_name varchar(255),
	route_desc varchar(255),
	route_type varchar(255),
	route_url varchar(255),
	route_color varchar(255),
	route_text_color varchar(255)
);

LOAD DATA LOCAL INFILE "routes.txt" INTO TABLE routes FIELDS TERMINATED BY "," ENCLOSED BY '"' IGNORE 1 LINES;

DROP TABLE IF EXISTS stop_times;

CREATE TABLE stop_times (
	trip_id varchar(255), 
	arrival_time TIME, 
	departure_time TIME,
	stop_id varchar(255),
	stop_sequence varchar(255),
	stop_headsign varchar(255),
	shape_dist_traveled varchar(255) 
);

LOAD DATA LOCAL INFILE "stop_times.txt" INTO TABLE stop_times FIELDS TERMINATED BY "," ENCLOSED BY '"'
IGNORE 1 LINES;


DROP TABLE IF EXISTS stops;

CREATE TABLE stops (
	stop_id varchar(255),
	stop_code varchar(255),
	stop_name varchar(255),
	stop_desc varchar(255),
	stop_lat varchar(255),
	stop_lon varchar(255),
	location_type varchar(255),
	parent_station varchar(255) 
);

LOAD DATA LOCAL INFILE "stops.txt" INTO TABLE stops FIELDS TERMINATED BY "," ENCLOSED BY '"' IGNORE 1 LINES;

DROP TABLE IF EXISTS transfers;

CREATE TABLE transfers (
	from_stop_id varchar(255),
	to_stop_id varchar(255),
	transfer_type varchar(255),
	min_transfer_time varchar(255) 
);

LOAD DATA LOCAL INFILE "transfers.txt" INTO TABLE transfers FIELDS TERMINATED BY "," ENCLOSED BY '"' IGNORE 1 LINES;

DROP TABLE IF EXISTS trips;

CREATE TABLE trips (
	route_id varchar(255),
	service_id varchar(255),
	trip_id varchar(255),
	trip_headsign varchar(255),
	trip_short_name varchar(255),
	direction_id varchar(255),
	shape_id varchar(255) 
);

LOAD DATA LOCAL INFILE "trips.txt" INTO TABLE trips FIELDS TERMINATED BY "," ENCLOSED BY '"' IGNORE 1 LINES;
/*
ALTER TABLE routes add column MT varchar(255) AFTER route_type;

UPDATE routes set MT='BUS' where route_type='3';

UPDATE routes set MT='METRO' where route_type='1';

UPDATE routes set MT='RER' where route_type='2';

UPDATE routes set MT='TRAM' where route_type='0';
*/

DROP TABLE IF EXISTS users;

CREATE TABLE users(
	user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	username varchar(60) NOT NULL UNIQUE,
	password varchar(255) NOT NULL,
	dateCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
	permission VARCHAR(5) CHECK ( permission IN ('admin','user') )
);

INSERT into users(username,password,permission) VALUES ("admin","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","admin");
INSERT into users(username,password,permission) VALUES ("user","04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb","user");
