create database theatreDB;

use theatreDB;

create table theatre_management(
booking_id int,
theatre_name varchar(150),
movie_name varchar(50),
show_date date,
show_time varchar(10),
seat_number varchar(10),
customer_name varchar(50),
phone_number varchar(15),
ticket_price double,
booking_status varchar(20)
);

select * from theatre_management;
