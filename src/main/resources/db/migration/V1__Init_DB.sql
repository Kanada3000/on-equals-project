create table category
(
    id        bigserial not null,
    long_name varchar(255),
    total     int4      not null,
    primary key (id)
);
create table city
(
    id      bigserial not null,
    city    varchar(255),
    country varchar(255),
    primary key (id)
);
create table city_employer
(
    employer_id int8 not null,
    city_id     int8 not null,
    primary key (employer_id, city_id)
);
create table city_resume
(
    resume_id int8 not null,
    city_id   int8 not null,
    primary key (resume_id, city_id)
);
create table city_seeker
(
    seeker_id int8 not null,
    city_id   int8 not null,
    primary key (seeker_id, city_id)
);
create table city_vacancy
(
    vacancy_id int8 not null,
    city_id    int8 not null,
    primary key (vacancy_id, city_id)
);
create table employer
(
    id             bigserial not null,
    age            int4      not null,
    description    varchar(255),
    email          varchar(255),
    emp_count      int4      not null,
    link_facebook  varchar(255),
    link_instagram varchar(255),
    link_linked_in varchar(255),
    link_twitter   varchar(255),
    name           varchar(255),
    site           varchar(255),
    size           int4      not null,
    category_id    int8,
    user_id        int8,
    primary key (id)
);
create table resume
(
    id          bigserial not null,
    description varchar(255),
    salary      int4      not null,
    category_id int8,
    type_id     int8,
    user_id     int8,
    primary key (id)
);
create table resume_like
(
    user_id   int8 not null,
    resume_id int8 not null,
    primary key (user_id, resume_id)
);
create table seeker
(
    id             bigserial not null,
    description    varchar(255),
    email          varchar(255),
    link_facebook  varchar(255),
    link_instagram varchar(255),
    link_linked_in varchar(255),
    link_twitter   varchar(255),
    name           varchar(255),
    site           varchar(255),
    category_id    int8,
    user_id        int8,
    primary key (id)
);
create table type
(
    id    bigserial not null,
    name  varchar(255),
    total int4      not null,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table usr
(
    id        bigserial not null,
    activated boolean,
    blocked   boolean,
    link      varchar(255),
    name      varchar(255),
    password  varchar(255),
    username  varchar(255),
    primary key (id)
);
create table vacancy
(
    id          bigserial not null,
    description varchar(255),
    salary      int4      not null,
    category_id int8,
    type_id     int8,
    user_id     int8,
    primary key (id)
);
create table vacancy_like
(
    user_id    int8 not null,
    vacancy_id int8 not null,
    primary key (user_id, vacancy_id)
);
alter table usr
    add constraint UK_a3tlwvai9gtkqhfgw0fcdio3l unique (link);
alter table city_employer
    add constraint FKls7gjtev7ij0io5mgfthjfo9r foreign key (city_id) references city;
alter table city_employer
    add constraint FK5dcrnim8p3ay6220vywrl4mge foreign key (employer_id) references employer;
alter table city_resume
    add constraint FKqy7iqs2wy322h0n2bu3o8ipku foreign key (city_id) references city;
alter table city_resume
    add constraint FKd0xrrkrgm1an9crv6s5a51jcp foreign key (resume_id) references resume;
alter table city_seeker
    add constraint FK5p2wwl6bw5ilhtx1qqxlpcbp3 foreign key (city_id) references city;
alter table city_seeker
    add constraint FKludtu37224yn9h6rfcttjr1ti foreign key (seeker_id) references seeker;
alter table city_vacancy
    add constraint FKa2ki2gur8cy1rs5hq388yhbou foreign key (city_id) references city;
alter table city_vacancy
    add constraint FK33wrvj35dh8qaaeyitt7r3ni8 foreign key (vacancy_id) references vacancy;
alter table employer
    add constraint FKri26eu0o6byskvh1rlyrnnii1 foreign key (category_id) references category;
alter table employer
    add constraint FKlrc4jrtvs0t02ni2pgt1unvpi foreign key (user_id) references usr;
alter table resume
    add constraint FKqk439pknt0fd6e3ky8fnc0dtd foreign key (category_id) references category;
alter table resume
    add constraint FKdk3bbqxu2i54wjhe6se3svrj7 foreign key (type_id) references type;
alter table resume
    add constraint FKnwnhvkapclpv0umh5ri96686e foreign key (user_id) references usr;
alter table resume_like
    add constraint FKao3egn467kd0kepjileswayu4 foreign key (resume_id) references resume;
alter table resume_like
    add constraint FKffv3bm4d7xmghs2usoqygppp9 foreign key (user_id) references usr;
alter table seeker
    add constraint FKc95pbjblcqskxnwu3630e7iqc foreign key (category_id) references category;
alter table seeker
    add constraint FKm7qllbokm9ydshdhuh7cwtwe0 foreign key (user_id) references usr;
alter table user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;
alter table vacancy
    add constraint FKjum1b9et3gb8yy1r9ag1tvv9w foreign key (category_id) references category;
alter table vacancy
    add constraint FK7brw5gwh97na2msp5uyvjiyyx foreign key (type_id) references type;
alter table vacancy
    add constraint FK58rdy6twq1e39s28kdb6i9suv foreign key (user_id) references usr;
alter table vacancy_like
    add constraint FKikl6ej0wyeek60e27g2f4ymhi foreign key (vacancy_id) references vacancy;
alter table vacancy_like
    add constraint FK1qi3nf3txgcjmk0u1i0n0bxex foreign key (user_id) references usr