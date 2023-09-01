USE company;

/*
This query script creates all the necessary for the company tables.

NOTE:
Every department must have one manager. For this reason, there is a one-to-one relation
between the manager table and the department table.
The username of both the managers and the employees
is tied to their id. Thus, in order not to have their ids overlaped it was decided
to start the employee id at 100.
*/

DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS worker;
DROP TABLE IF EXISTS department;



CREATE TABLE department (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    employee_count INT DEFAULT 0,
    min_budget INT DEFAULT 0
) AUTO_INCREMENT = 1;

CREATE TABLE worker (
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    department_id INT,
    email VARCHAR(50) CHECK (email LIKE '%@gmail.com'),
    employed_since DATE,
    vacation DATE,
    salary DECIMAL(10, 2),
    type ENUM('MANAGER', 'EMPLOYEE'),
    FOREIGN KEY (department_id) REFERENCES department(id) -- Many-to-one relationship
) AUTO_INCREMENT = 1;

CREATE TABLE manager (
    id INT PRIMARY KEY,
    progress_report VARCHAR(100),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE employee (
	id INT PRIMARY KEY,
	position VARCHAR(50),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE user (
	id INT PRIMARY KEY,
    password VARCHAR(68),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE role (
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    role ENUM('MANAGER', 'EMPLOYEE'),
    FOREIGN KEY (user_id) REFERENCES user(id) -- Many-to-one relationship
);