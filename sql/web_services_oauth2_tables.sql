drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
)
WITH (
OIDS = FALSE
);
ALTER TABLE oauth_client_details
  OWNER TO service;

drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
)
WITH (
OIDS = FALSE
);
ALTER TABLE oauth_client_token
  OWNER TO service;

drop table if exists oauth_access_token;
create table oauth_access_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BYTEA,
  refresh_token     VARCHAR(256)
)
WITH (
OIDS = FALSE
);
ALTER TABLE oauth_access_token
  OWNER TO service;

drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BYTEA,
  authentication BYTEA
)
WITH (
OIDS = FALSE
);
ALTER TABLE oauth_refresh_token
  OWNER TO service;

drop table if exists oauth_code;
create table oauth_code (
  code           VARCHAR(256),
  authentication BYTEA
)
WITH (
OIDS = FALSE
);
ALTER TABLE oauth_code
  OWNER TO service;

drop table if exists oauth_approvals;
create table oauth_approvals (
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
)
WITH (
OIDS = FALSE
);
ALTER TABLE oauth_approvals
  OWNER TO service;

drop table if exists client_details;
create table client_details (
  appId                  VARCHAR(256) PRIMARY KEY,
  resourceIds            VARCHAR(256),
  appSecret              VARCHAR(256),
  scope                  VARCHAR(256),
  grantTypes             VARCHAR(256),
  redirectUrl            VARCHAR(256),
  authorities            VARCHAR(256),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(256)
)
WITH (
OIDS = FALSE
);
ALTER TABLE client_details
  OWNER TO service;