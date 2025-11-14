CREATE DATABASE rotom;

CREATE TABLE rotom.accounts (
  uuid BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) NOT NULL PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  userpassword VARCHAR(255) NOT NULL
);
CREATE user 'rotom-accounts'@'localhost' identified BY 'password';
GRANT INSERT, SELECT, UPDATE, DELETE ON rotom.accounts TO 'rotom-accounts'@'localhost';

