drop table if exists USERS CASCADE;
drop table if exists ITEMS CASCADE;
-- drop table if exists BOOKINGS CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name  VARCHAR(255) UNIQUE                                 NOT NULL,
    email VARCHAR(512) UNIQUE                                NOT NULL

);

CREATE TABLE IF NOT exists ITEMS
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name        varchar(255) UNIQUE,
    description varchar(255),
    available   boolean,
    owner       integer,
    request     integer
);

-- CREATE TABLE IF NOT exists BOOKINGS
-- (
--     id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
--     start  timestamp not null,
--     end    timestamp not null,
--     item   bigint    not null,
--     booker bigint    not null,
--     status bigint   not null
-- )