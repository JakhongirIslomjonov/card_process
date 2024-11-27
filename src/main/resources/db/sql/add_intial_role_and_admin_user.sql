CREATE EXTENSION IF NOT EXISTS pgcrypto;

create table roles
(
    id   uuid primary key default gen_random_uuid(),
    name varchar(50) not null unique
);

insert into roles(name)
values ('ROLE_ADMIN'),
       ('ROLE_CLIENT');

create table users
(
    id        uuid primary key default gen_random_uuid(),
    full_name varchar(100) not null,
    email     varchar(100) not null unique,
    password  varchar(255) not null
);

create table user_roles
(
    user_id  uuid not null,
    roles_id uuid not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (roles_id) references roles (id) on delete cascade
);

insert into users(full_name, email, password)
values ('John', 'admin@gmail.com', '$2a$10$OqhtZC0.Dcm1zFd5b0N5xOg/Yq6cZrhPpeimq2/CYzU6vfsWJ1smy'),
       ('Sarah', 'client@gmail.com', '$2a$10$OqhtZC0.Dcm1zFd5b0N5xOg/Yq6cZrhPpeimq2/CYzU6vfsWJ1smy');


insert into user_roles (user_id, roles_id)
values ((Select id from users where email = 'admin@gmail.com'), (Select id from roles where name = 'ROLE_ADMIN')),
       ((Select id from users where email = 'client@gmail.com'), (Select id from roles where name = 'ROLE_CLIENT'))