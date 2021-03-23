Create DATABASE gtfs Default character set utf8 default collate utf8_general_ci;

USE gtfs;

create table agency (
	agency_id varchar(255) NOT NULL PRIMARY KEY,
	agency_name varchar(255),
	agency_url varchar(255),
	agency_timezone varchar(255),
	agency_lang varchar(255),
	agency_phone varchar(255)
	);

LOAD DATA INFILE "agency.txt" INTO TABLE agency FIELDS TERMINATED BY "," IGNORE 1 LINES;


create table calendar (
	service_id varchar(255) NOT NULL PRIMARY KEY,
	monday varchar(255),
	tuesday varchar(255),
	wednesday varchar(255),
	thursday varchar(255),
	friday varchar(255),
	saturday varchar(255),
	sunday varchar(255),
	start_date varchar(255),
	end_date varchar(255)
);

LOAD DATA INFILE "calendar.txt" INTO TABLE calendar FIELDS TERMINATED BY "," IGNORE 1 LINES;

create table calendar_dates (
	service_id varchar(255) NOT NULL,
	date varchar(255),
	exception_type varchar(255),
	PRIMARY KEY(service_id,date)
);

LOAD DATA INFILE "calendar_dates.txt" INTO TABLE calendar_dates FIELDS TERMINATED BY "," IGNORE 1 LINES;

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

LOAD DATA INFILE "routes.txt" INTO TABLE routes FIELDS TERMINATED BY "," IGNORE 1 LINES;

CREATE TABLE stop_times (
	trip_id varchar(255), 
	arrival_time varchar(255), 
	departure_time varchar(255),
	stop_id varchar(255),
	stop_sequence varchar(255),
	stop_headsign varchar(255),
	shape_dist_traveled varchar(255) 
);

LOAD DATA INFILE "stop_times.txt" INTO TABLE stop_times FIELDS TERMINATED BY "," IGNORE 1 LINES;


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

LOAD DATA INFILE "stops.txt" INTO TABLE stops FIELDS TERMINATED BY "," IGNORE 1 LINES;

CREATE TABLE transfers (
	from_stop_id varchar(255),
	to_stop varchar(255),
	stop_id varchar(255),
	transfer_type varchar(255),
	min_transfer_time varchar(255) 
);

LOAD DATA INFILE "transfers.txt" INTO TABLE transfers FIELDS TERMINATED BY "," IGNORE 1 LINES;

CREATE TABLE trips (
	route_id varchar(255),
	service_id varchar(255),
	trip_id varchar(255),
	trip_headsign varchar(255),
	trip_short_name varchar(255),
	direction_id varchar(255),
	shape_id varchar(255) 
);

LOAD DATA INFILE "trips.txt" INTO TABLE trips FIELDS TERMINATED BY "," IGNORE 1 LINES;
/*
ALTER TABLE routes add column MT varchar(255) AFTER route_type;

UPDATE routes set MT='BUS' where route_type='3';

UPDATE routes set MT='METRO' where route_type='1';

UPDATE routes set MT='RER' where route_type='2';

UPDATE routes set MT='TRAM' where route_type='0';
*/
