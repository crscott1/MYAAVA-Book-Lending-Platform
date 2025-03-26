# MYAAVA-Book-Lending-Platform

## 0. Build Up
### Environment
- jdk 1.8
- mysql 8.0
- maven 4.0.0
- SpringBoot 3.4.3

### Set Up
After you install all the environment, we can start.
#### Since I have already setup Maven, SprintBoot and dependencies in pom.xml, you can just open it in your development tool. 
#### If you want to build it up by youseself, please follow the steps.
I build the project in IntelliJ IDEA, you can either use VS Code or Eclipse.
1. Create new SpringBoot project. IntelliJ IDEA was equipped with Spring Initializr.
2. We use maven management and Java programming. Group is the domain of orginazation. Artifact is the project name.

![image](https://github.com/user-attachments/assets/6c46703f-2c33-4893-a4b1-b2352697b7df)



3. Next, I choose some denpendencies we might need in future development. These dependencies will generate a pom.xml for Maven. If we need more other dependencies in development process, we can modify the pom.xml
   
  Mark the box when chosing Dependencies
  - Developer Tools: Spring Boot Devtools, Lombok
  - Web: Spring Web
  - Tempelate Engines: Thymeleaf
  - SQL: JDBC API, MyBatis Framework, MySQL Driver
![image](https://github.com/user-attachments/assets/a23f4bf8-6507-419c-b91c-f873849dd831)

  
4. Finsih build up the project. And here is the structure of the project.
   ```
      community_book_ledning_system/
      │
      ├── src/                  # source code root directionary
      │   ├── main/              # main application code
      │   │   └── java/          # Java sorece code
      │   │       └── edu.arizona.csc536.book_lending_system         # package name，all java class inside it
      │   ├── test/              # test code
      │
      ├── resources/            # resource file
      │   ├── application.properties # main equipment file of database
      │   └── static/             # static file such as CSS, JS, HTML and etc.
      │
      ├── pom.xml                # Maven equipment file
      └── README.md              # project description
   ```

## 1. Run
Before running, make sure to install Mysql.
1. Build database
   -  Login to mysql with username and password on local PC. Once we have server, change it to the server username and password.
      ```
      mysql -u username -p
      ```
   - ceate the databse
     ```
      CREATE DATABASE IF NOT EXISTS `book-lending-system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
     ```
     Then you can have a connection with your local MySQL server databse successfully.
     ![image](https://github.com/user-attachments/assets/2cfcfdba-6f6e-4189-9e82-da4ab815eddd)

   - Charles can create SQL table under the databse. Write your code under `src/main/resources/db/book_lending_system.sql`
     ```
     source src/main/resources/db/book_lending_system.sql;
     ```
     ![image](https://github.com/user-attachments/assets/4fd3dc77-2eab-406f-b231-dca36174b0ab)

2. Equip application's configuration files(`application.properties`) include the necessary DataSource settings, especially the JDBC URL. Change the username and password to your local mysql username and password.
   ```
   spring.application.name=book_lending_system
   spring.datasource.url=jdbc:mysql://localhost:3306/book-lending-system
   spring.datasource.username=yourUsername
   spring.datasource.password=yourPassword
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
   
4. The primary startup file is typically located at `src/main/java/edu/arizona/csc536/coomunity_book_lending_system/CoomunityBookLendingSystemApplication.java`. This file usually contains the main method, which is the entry point for a Spring Boot application. It configures the application automatically by extending the SpringBootApplication class and scans for related components, allowing you to start the application either from the IDE or via the command line by executing the following command:
```
   mvn spring-boot:run
```
If you suceesfully run the project with SpringBoot based on the database, you can see prompt on terminal.

![image](https://github.com/user-attachments/assets/fcf0914f-9775-4877-8e76-32a4955207dd)


## 2. Development Logic
Before that, run sql file in MySQL. Make sure the database and table name are the same.   
![image](https://github.com/user-attachments/assets/7309db18-50a1-44ba-9a69-a815e10b86ca)    

### Main Business Logic Directory: src/main
1. java/edu.arizona.csc536.book_lending_system (Main Package) : This is the main package that contains various modules and follows the standard structure of a Spring Boot project.
   - controller: Controller Layer
     Receives requests from the frontend (or external clients). Processes request parameters and paths. Calls the service layer to execute business logic. Returns the result (e.g., JSON) to the frontend
     Responsible for handling user requests (HTTP), and calling the service layer to process logic and return results.  
     It contains interfaces for adding books, adding borrowers, deleting books, borrowing books, etc.
     - AdminController: Interfaces related to administrator functions, such as login, permission management, and adding/deleting users.
     - BookController: Interfaces for book management, such as adding new books, deleting books, updating book information, and searching books by category.
     - BorrowingController: Interfaces for borrowing actions, such as borrowing books, returning books, and viewing borrowing records.
     - UserController: Interfaces for user-side operations, such as user registration, updating personal information, and viewing personal borrowing history.

   - domain: Entity/Model Layer  
     Defines the core data structures, objecta and variables of the system, such as Book, User, etc, which is corresponding to entities in SQL tables.   
     - Admin, User:  Entity classes for administrator and regular users.   
     - Book: Entity class representing books.  
     - BookCategory: Entity class for book categories, e.g., "Computer Science", "History", etc.   
     - BorrowingBooks: Entity class for borrowing records, which may include borrow and return dates.  
     - Example Classes:  AdminExample, BookExample, BorrowingBooksExample, etc.
       Mostly generated by MyBatis Generator. Dynamic SQL query, instead of Java.
     - Vo Package: View Object: Used to encapsulate data exchanged between the frontend and backend.  
      For example: a list of borrowing records returned to the frontend, including fields like book title, borrower, and borrow date.  
      Used for dynamically building SQL queries (e.g., for pagination or fuzzy search).  

   - mapper: Mapping Layer (MyBatis Mapper)  
     Handles database operations and maps domain objects to database tables.

   - service: Business Logic Layer
     Core business logic, such as borrowing and returning books, user login checks, etc.
     
   - utils.page: Utility Package
     A helper package commonly used for handling pagination (e.g., for displaying paginated lists in admin panels or user interfaces).
     


   
