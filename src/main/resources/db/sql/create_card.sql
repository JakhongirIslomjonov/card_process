create table card
(
    id         uuid primary key      default gen_random_uuid(),
    user_id    bigint       not null,
    status     varchar(255) not null,
    balance    bigint,
    currency   varchar(255) not null,
    create_at  timestamp    not null default current_timestamp,
    update_at  timestamp    not null default current_timestamp,
    created_by bigint,
    updated_by bigint,
    foreign key (user_id) references users (id) on delete cascade
);

insert into card(user_id, status, balance, currency, created_by, updated_by)
VALUES (1, 'ACTIVE', 100000, 'UZS', 1, 1);
