-- create table city_employer
-- (
--     employer_id int8 not null,
--     city_id     int8 not null,
--     primary key (employer_id, city_id)
-- );
--
-- create table city_resume
-- (
--     resume_id int8 not null,
--     city_id   int8 not null,
--     primary key (resume_id, city_id)
-- );
--
-- create table city_seeker
-- (
--     seeker_id int8 not null,
--     city_id   int8 not null,
--     primary key (seeker_id, city_id)
-- );
--
-- create table city_vacancy
-- (
--     vacancy_id int8 not null,
--     city_id    int8 not null,
--     primary key (vacancy_id, city_id)
-- );

create table page
(
    id           bigserial not null,
    created_date timestamp,
    full_body    text,
    label        varchar(20),
    name         varchar(200),
    short_body   varchar(500),
    primary key (id)
);

create table story
(
    id           bigserial not null,
    created_date timestamp,
    image        varchar(50),
    text         text,
    title        varchar(200),
    primary key (id)
);

create table sticker
(
    id           bigserial not null,
    created_date timestamp,
    image        varchar(50),
    text         text,
    title        varchar(200),
    primary key (id)
)