# Users schema

# --- !Ups

create table "people" (
  # --- "id" bigint generated by default as identity(start with 1) not null primary key,
  "id" serial primary key,
  "name" varchar not null,
  "age" int not null
);

# --- !Downs

drop table "people" if exists;
