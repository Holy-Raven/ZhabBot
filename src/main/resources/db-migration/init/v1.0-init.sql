CREATE SCHEMA IF NOT EXISTS zhab;

CREATE TABLE users
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    family      VARCHAR(255) NOT NULL,
    create_time TIMESTAMP NOT NULL,
);