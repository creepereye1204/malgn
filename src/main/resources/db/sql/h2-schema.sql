create table members
(
    id                 bigint primary key      not null auto_increment,
    username           varchar(50)             unique not null,
    password           varchar(255)            not null,
    role               varchar(20)             not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp
);

create table contents
(
    id                 bigint primary key      not null auto_increment,
    title              varchar(100)            not null,
    description        text,
    view_count         bigint                  not null default 0,
    created_date       timestamp               not null,
    created_by         varchar(50)             not null,
    last_modified_date timestamp,
    last_modified_by   varchar(50)
);
