drop database if exists web_services_oauth;
CREATE DATABASE web_services_oauth
WITH OWNER = service
ENCODING = 'UTF8'
TABLESPACE = pg_default
LC_COLLATE = 'English_United States.1252'
LC_CTYPE = 'English_United States.1252'
CONNECTION LIMIT = -1;

drop table if exists oauth_client_details;
create table oauth_client_details(
  client_id varchar(300) primary key,
  authorized_grant_types varchar(300),
  authorities varchar(300),
  scope varchar(300),
  client_secret varchar(300),
  access_token_validity integer,
  refresh_token_validity integer
)

WITH (
     OIDS=FALSE
     );
ALTER TABLE public.oauth_client_details
  OWNER TO service;

drop table if exists oauth_client_token;
create table oauth_client_token(
  token_id varchar(300),
  client_id varchar(300),
  token bytea,
  authentication_id varchar(300) primary key,
  username varchar(300)
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_client_token
  OWNER TO service;

drop table if exists oauth_access_token;
create table oauth_access_token(
  token_id varchar(300),
  client_id varchar(300),
  token bytea,
  authentication_id varchar(300) primary key,
  username varchar(300),
  authentification bytea,
  refresh_token varchar(300)
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_access_token
  OWNER TO service;

drop table if exists oauth_refresh_token;
create table oauth_refresh_token(
  token_id varchar(300),
  token bytea,
  authentification bytea
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_refresh_token
  OWNER TO service;

drop table if exists oauth_code;
create table oauth_code(
  code varchar(300),
  authentication bytea
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_code
  OWNER TO service;