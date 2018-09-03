CREATE DATABASE web_services_oauth
WITH OWNER = service
ENCODING = 'UTF8'
TABLESPACE = pg_default
LC_COLLATE = 'English_United States.1252'
LC_CTYPE = 'English_United States.1252'
CONNECTION LIMIT = -1;

create table oauth_client_details(
  client_id bigserial not null primary key,
  authorized_grant_types varchar(100),
  authorities varchar(100),
  scope varchar(300),
  client_secret varchar(500),
  access_token_validity integer,
  refresh_token_validity integer
)

WITH (
     OIDS=FALSE
     );
ALTER TABLE public.oauth_client_details
  OWNER TO service;

create table oauth_client_token(
  token_id bigserial not null primary key,
  client_id bigserial not null references oauth_client_details(client_id) on delete cascade,
  token varchar(500),
  authentication_id varchar(100) not null,
  username varchar(50)
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_client_token
  OWNER TO service;

create table oauth_access_token(
  token_id bigserial not null primary key,
  client_id bigserial not null references oauth_client_details(client_id) on delete cascade,
  token varchar(500),
  authentication_id varchar(100) not null,
  username varchar(50),
  authentification varchar(500),
  refresh_token varchar(500)
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_access_token
  OWNER TO service;

create table oauth_code(
  code varchar(500),
  authentication varchar(50)
)

WITH (
OIDS=FALSE
);
ALTER TABLE public.oauth_code
  OWNER TO service;