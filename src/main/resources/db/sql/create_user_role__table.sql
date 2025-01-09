create table roles
(
    id        bigserial primary key,
    role_name varchar(255) not null unique
);

insert into roles(role_name)
values ('ROLE_ADMIN'),
       ('ROLE_CLIENT');

create table users
(
    id        bigserial primary key,
    full_name varchar(20) not null,
    email     varchar(30) not null,
    password  varchar(8) not null
);

create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (role_id) references roles (id) on delete cascade
);

insert into users(full_name, email, password)
values('Islomjonnov Jaxongir','jaxongirnamti@gmail.com','12345678');


insert into user_roles(user_id, role_id) values (1 ,1),(1,2);