CREATE DATABASE multiventasdata;
USE DATABASE multiventasdata;
CREATE TABLE establishments
  (
    id INT (10) ,
    name VARCHAR (40)
  ) ;
ALTER TABLE establishments ADD CONSTRAINT establishment_PK PRIMARY KEY ( id ) ;