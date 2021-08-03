drop table if exists product;
create table product
(
    id    serial,
    title character varying(155) NOT NULL,
    cost  money                  NOT NULL,
    primary key (id)
);

drop table if exists shop_order;
create table shop_order
(
    id      serial,
    user_id integer references users (id) not null,
    status  varchar(50) default 'NEW_ORDER',
    primary key (id)
);

drop table if exists order_item;
create table order_item
(
    id            serial,
    shop_order_id integer references shop_order (id) not null,
    product_id    integer references product (id)    not null,
    quantity      integer default 1,
    primary key (id, shop_order_id, product_id)
);