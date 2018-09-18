drop database if exists web_services;
CREATE DATABASE web_services;

drop table if exists web_services.customers;
create table web_services.customers (
  customer_id bigint auto_increment not null unique primary key,
  first_name  varchar(50),
  last_name   varchar(50),
  age         integer
);

drop table if exists web_services.orders;
create table web_services.orders (
  order_id    bigint auto_increment not null unique primary key,
  customer_id bigint not null references customers (customer_id) on delete cascade,
  trans_id    bigint not null,
  good        varchar(50),
  good_weight double precision,
  price       double precision
);

drop table if exists web_services.photos;
CREATE TABLE web_services.photos
(
  photo_id    bigint auto_increment NOT NULL unique primary key,
  customer_id bigint not null references customers (customer_id) on delete cascade,
  file_name   varchar(100),
  file_url    varchar(150),
  file_type   varchar(50),
  file_size   integer
);