set names utf8;

drop database if exists st4db;
create database st4db character set utf8 collate utf8_bin;
use st4db;

create table roles
(
    id   int         not null primary key,
    name varchar(15) not null unique
);


insert into roles
values (0, 'client');
insert into roles
values (1, 'admin');

create table users
(
    id         int         not null auto_increment primary key,
    login      varchar(20) not null unique,
    password   varchar(20) not null,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    role_id    int         not null default 0 references roles (id) on update restrict on DELETE cascade,
    balance    double      not null default 0
);

insert into users
VALUES (default, 'admin', 'admin', 'Evgen', 'Kravchenko', 1, default);
insert into users
VALUES (default, 'client', 'client', 'Alex', 'Vazovski', 0, default);

create table services
(
    id   int primary key auto_increment,
    name varchar(30) unique
);

insert into services
VALUES (default, 'TV');
insert into services
VALUES (default, 'Phone');
insert into services
VALUES (default, 'Internet');

create table users_tariffs
(
    user_id   int references users (id) on delete cascade,
    tariff_id int references tariffs (id) on delete cascade,
    UNIQUE (user_id, tariff_id)
);

/*insert into users_tariffs
VALUES (1, 3);
insert into users_tariffs
VALUES (1, 4);*/

create table tariffs
(
    id         int primary key auto_increment,
    name       varchar(30) not null,
    price      double default 100,
    service_id int         references services (id) on delete set null,
    UNIQUE (name, service_id)
);

insert into tariffs
VALUES (default, 'simple', 100, 1);
insert into tariffs
VALUES (default, 'medium', 300, 1);
insert into tariffs
VALUES (default, 'max', 500, 1);
insert into tariffs
VALUES (default, 'simple', 100, 2);
insert into tariffs
VALUES (default, 'medium', 300, 2);
insert into tariffs
VALUES (default, 'max', 500, 3);


