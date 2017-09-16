# SpringJDBC_CRUD-SQLServer

SpringJDBC_CRUD -- SQLServer2012

The application is used to
Inserts a new record.
Update the already existing records in the table.
Searches for a particular record.
Deletes a particular record from DB.


//SQLServer Create table
CREATE TABLE ride (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) NOT NULL,
    duration int
);

ALTER TABLE ride ADD ride_date DATETIME;
