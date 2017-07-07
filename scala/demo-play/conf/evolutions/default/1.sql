# Users schema

# --- !Ups

CREATE TABLE "people" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "name" varchar(32) NOT NULL,
  "age" int NOT NULL,
  PRIMARY KEY("id")
);

# --- !Downs
DROP TABLE "people" IF_EXISTS;