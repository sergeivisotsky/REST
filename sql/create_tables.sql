create table customers(
   customer_id bigserial not null primary key,
   first_name varchar(50),
   last_name varchar(50),
   age integer
)

WITH (
  OIDS=FALSE
);
ALTER TABLE public.customers
  OWNER TO service;

create table orders(
   order_id bigserial not null primary key,
   customer_id bigserial not null references customers(customer_id) on delete cascade,
   trans_id bigserial not null,
   good varchar(50),
   good_weight double precision,
   price double precision
)

WITH (
  OIDS=FALSE
);
ALTER TABLE public.orders
  OWNER TO service;

CREATE TABLE public.photos
(
  photo_id bigserial NOT NULL primary key,
  customer_id bigserial not null references customers(customer_id) on delete cascade,
  file_name varchar(100),
  file_url varchar(150),
  file_type varchar(50),
  file_size integer
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.photos
  OWNER TO service;