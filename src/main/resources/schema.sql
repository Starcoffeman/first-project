drop table if exists USERS CASCADE;
drop table if exists ITEMS CASCADE;
drop table if exists BOOKINGS CASCADE;
drop table if exists lastBooking CASCADE;
drop table if exists nextBooking CASCADE;


CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name  VARCHAR(255)                                  NOT NULL,
    email VARCHAR(512)                                 NOT NULL

);

CREATE TABLE IF NOT EXISTS items
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name        varchar(255) UNIQUE,
    description varchar(255),
    available   boolean,
    owner       integer,
    request     integer
);

CREATE TABLE IF NOT EXISTS booking_status
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name   varchar(255)
    );

INSERT INTO booking_status (name) VALUES ('WAITING');
INSERT INTO booking_status (name) VALUES ('APPROVED');
INSERT INTO booking_status (name) VALUES ('REJECTED');

CREATE TABLE IF NOT EXISTS bookings
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    start  timestamp  not null,
    finish   timestamp   not null,
    itemId   integer   REFERENCES items(id),
    booker integer   REFERENCES users(id),
    status integer   REFERENCES booking_status(id)
);

