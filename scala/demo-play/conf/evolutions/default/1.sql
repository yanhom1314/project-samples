# Users schema

# --- !Ups

CREATE TABLE "t_people" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "name" varchar(32) NOT NULL,
  "age" int NOT NULL,
  PRIMARY KEY("id")
);

# --- !Downs
DROP TABLE "t_people" IF_EXISTS;