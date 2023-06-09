
alter table if exists comments drop constraint if exists FKh4c7lvsc298whoyd4w9ta25cr;
alter table if exists follows drop constraint if exists FKf11by634j00e8h8ntxe7hkv4s;
alter table if exists follows drop constraint if exists FKqnkw0cwwh6572nyhvdjqlr163;
alter table if exists posts drop constraint if exists FK6xvn0811tkyo3nfjk2xvqx6ns;
alter table if exists tags drop constraint if exists FK2xx35w0t4lvxghc823ho784g4;
drop table if exists chats cascade;
drop table if exists comments cascade;
drop table if exists follows cascade;
drop table if exists posts cascade;
drop table if exists tags cascade;
drop table if exists users cascade;
create table chats (id serial not null, message varchar(255), send_date date, user_from varchar(255), user_read boolean not null, user_to varchar(255), primary key (id));
create table comments (id bigserial not null, post_id bigint, text varchar(255), primary key (id));
create table follows (id bigserial not null, author_id bigint, follower_id bigint, primary key (id));
create table posts (id bigserial not null, author_id bigint, creation_date date, description varchar(255), photo_url varchar(255), title varchar(255), primary key (id));
create table tags (item_id bigint not null, name varchar(255));
