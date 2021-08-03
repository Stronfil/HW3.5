drop table if exists users;
create table users
(
    id       serial,
    login    varchar(50) DEFAULT NULL,
    password varchar(68) DEFAULT NULL,
    PRIMARY KEY (id)
);

drop table if exists roles;
create table roles
(
    id   serial,
    name varchar(50) DEFAULT NULL,
    PRIMARY KEY (id)
);

drop table if exists user_role;
create table user_role
(
    user_id integer not null references users (id),
    role_id integer not null references roles (id),
    primary key (user_id, role_id)
);