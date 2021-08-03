insert into users (login, password)
values ('user', '$2y$12$mO.X16/LtQ.b5Qj6ClK9peW6WoeM3iQYfeQw/qMA9frkhrLcMJvT6'),
       ('admin', '$2y$12$mO.X16/LtQ.b5Qj6ClK9peW6WoeM3iQYfeQw/qMA9frkhrLcMJvT6');

insert into roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into user_role (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2);
