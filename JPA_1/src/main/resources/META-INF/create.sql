CREATE TABLE AUTHOR ("ID" INTEGER IDENTITY PRIMARY KEY, "FIRSTNAME" VARCHAR(50) not null, "SECONDNAME" VARCHAR(50) not null);
CREATE TABLE LIBRARY ("ID" INTEGER IDENTITY PRIMARY KEY, "TITLE" VARCHAR(50) not null, "AUTHOR" INTEGER, "AVAILABLE" BOOLEAN not null, FOREIGN KEY ("AUTHOR") REFERENCES AUTHOR("ID"));
