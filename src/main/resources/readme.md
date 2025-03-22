# Connecting to your database

## Install MySQL
MySQL version 5.7 or above should be installed on your machine. Configure MySQL such as password, etc.   
After that, you can create a database locally through MySQL Command Line Client.

## Create the Database
Create a database named 'book_lending_system' using the following SQL command. Make sure the character set is utf8mb4 and the collation is utf8mb4_general_ci.
``` sql
CREATE DATABASE IF NOT EXISTS `book_lending_system` 
DEFAULT CHARSET utf8mb4 
COLLATE utf8mb4_general_ci;
```
You can use following command to check whether the creation is successful.
``` sql
SHOW DATABASES;
```

## Import SQL File
Import the SQL schema and initial data from file 'book_lending_system.sql' with following command.
``` sql
USE book_lending_system; -- Select the database
SOURCE /your/path/book_lending_system.sql; -- Import the SQL file
```
After this, you can use following command to check:
``` sql
SHOW TABLES;
```
You are expected to get an output similar to the following. (Note that my local database is named 'library-manager-system')   

<img width="862" alt="7f9acb966dec495213af994e9674137" src="https://github.com/user-attachments/assets/e6d7fcff-6fea-4c72-8c5a-6c3707acbc93" />

## Show Data
Then you can use the following command to check some data in the database.
``` sql
SELECT * FROM user LIMIT 5;
```
You will get output similar to the following:

<img width="417" alt="36bb3306a09cd9c3f78b9876d947eb8" src="https://github.com/user-attachments/assets/0a1e783f-404c-4255-a426-47120efdaa18" />
