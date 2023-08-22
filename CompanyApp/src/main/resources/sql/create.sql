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

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;


CREATE TABLE department (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    employee_count INT,
    min_budget INT
);

CREATE TABLE user (
	id INT PRIMARY KEY,
    password VARCHAR(68)
);

CREATE TABLE manager (
    department_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    employed_since DATE,
    vacation DATE,
    salary DECIMAL(10, 2),
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (department_id) REFERENCES user(id)
);

CREATE TABLE employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    department_id INT,
    position VARCHAR(50),
    employed_since DATE,
    vacation DATE,
    salary DECIMAL(10, 2),
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (id) REFERENCES user(id)
) AUTO_INCREMENT = 100;

CREATE TABLE role (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    role VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES user(id)
);