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
);