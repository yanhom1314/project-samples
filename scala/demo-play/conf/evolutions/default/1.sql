# Users schema

# --- !Ups

create table "people" (
  "id" bigint auto_increment primary key,
  "name" varchar not null,
  "age" int not null
);

# --- !Downs

drop table "people" if exists;
