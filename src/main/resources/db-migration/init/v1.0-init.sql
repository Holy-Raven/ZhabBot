CREATE SCHEMA IF NOT EXISTS zhab;

CREATE TABLE IF NOT EXISTS zhab.follower
(
    id          BIGINT                                                                                      NOT NULL,
    name        VARCHAR(255)                                                                                NOT NULL,
    family      VARCHAR(255)                                                                                NOT NULL,
    create_time TIMESTAMP                                                                                   NOT NULL,

    CONSTRAINT pk_follower PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS zhab.news
(
    id          BIGINT                  GENERATED BY DEFAULT AS IDENTITY                                    NOT NULL,
    title       VARCHAR(250)                                                                                NOT NULL,
    message     VARCHAR(2000)                                                                               NOT NULL,
    create_time TIMESTAMP                                                                                   NOT NULL,

    CONSTRAINT pk_news PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS zhab.bands
(
    id          BIGINT                  GENERATED BY DEFAULT AS IDENTITY                                    NOT NULL,
    name        VARCHAR(100)                                                                                NOT NULL,
    city        VARCHAR(100)                                                                                NOT NULL,
    genre       VARCHAR(100),
    description VARCHAR(2000),
    link        VARCHAR(250),

    CONSTRAINT pk_bands PRIMARY KEY (id),
    CONSTRAINT uq_bands_name_city UNIQUE (name, city)
);

CREATE TABLE IF NOT EXISTS zhab.places
(
    id          BIGINT                  GENERATED BY DEFAULT AS IDENTITY                                    NOT NULL,
    name        VARCHAR(100)                                                                                NOT NULL,
    city        VARCHAR(100)                                                                                NOT NULL,
    description VARCHAR(2000),
    link        VARCHAR(250),

    CONSTRAINT pk_place PRIMARY KEY (id),
    CONSTRAINT uq_place_name_city UNIQUE (name, city)
);

CREATE TABLE IF NOT EXISTS zhab.concerts
(
    id          BIGINT                  GENERATED BY DEFAULT AS IDENTITY                                    NOT NULL,
    title       VARCHAR(100)                                                                                NOT NULL,
    description VARCHAR(2000)                                                                               NOT NULL,
    price       INTEGER                                                                                     NOT NULL,
    start_time  TIMESTAMP                                                                                   NOT NULL,
    end_time    TIMESTAMP,
    link        VARCHAR(250),

    place_id    BIGINT,

    CONSTRAINT fk_concerts_place_id FOREIGN KEY (place_id) REFERENCES zhab.places (id) ON DELETE SET NULL,
    CONSTRAINT pk_concerts PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS zhab.concert_band
(
   concert_id BIGINT                                                                                       NOT NULL,
   band_id    BIGINT                                                                                       NOT NULL,

   CONSTRAINT pk_concerts_bands PRIMARY KEY (concert_id, band_id),
   CONSTRAINT fk_concerts_concerts_bands FOREIGN KEY (concert_id) REFERENCES zhab.concerts (id) ON DELETE CASCADE,
   CONSTRAINT fk_bands_concerts_bands FOREIGN KEY (band_id) REFERENCES zhab.bands (id) ON DELETE CASCADE
);
