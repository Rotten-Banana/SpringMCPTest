create table if not exists products (
    id serial primary key not null,
    name varchar(32) not null,
    type varchar(32) not null,
    price float not null
);

create table if not exists inventory (
    id serial primary key not null,
    pid int references products (id),
    availability int not null
);

create table if not exists orders (
    id serial primary key not null,
    order_date_time date,
    total float not null
);

create table if not exists orders_line_item (
    id serial primary key not null,
    pid int references products (id),
    oid int references orders (id),
    count int not null
)