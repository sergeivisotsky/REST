drop database if exists web_services;
CREATE DATABASE web_services
WITH OWNER = service
ENCODING = 'UTF8'
TABLESPACE = pg_default
LC_COLLATE = 'English_United States.1252'
LC_CTYPE = 'English_United States.1252'
CONNECTION LIMIT = -1;

drop table if exists customers;
create table customers (
  customer_id bigserial not null primary key,
  first_name  varchar(50),
  last_name   varchar(50),
  age         integer
)

WITH (
OIDS = FALSE
);
ALTER TABLE public.customers
  OWNER TO service;

drop table if exists orders;
create table orders (
  order_id    bigserial not null primary key,
  customer_id bigserial not null references customers (customer_id) on delete cascade,
  trans_id    bigserial not null,
  good        varchar(50),
  good_weight double precision,
  price       double precision
)

WITH (
OIDS = FALSE
);
ALTER TABLE public.orders
  OWNER TO service;

drop table if exists photos;
CREATE TABLE photos
(
  photo_id    bigserial NOT NULL primary key,
  customer_id bigserial not null references customers (customer_id) on delete cascade,
  file_name   varchar(100),
  file_url    varchar(150),
  file_type   varchar(50),
  file_size   integer
)
WITH (
OIDS = FALSE
);
ALTER TABLE public.photos
  OWNER TO service;