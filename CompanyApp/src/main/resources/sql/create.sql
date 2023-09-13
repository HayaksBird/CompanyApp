USE company;

/*
This query script creates all the necessary for the company tables.

NOTE:
It is foreseen that the company might have different types of workers with different requiered fields. 
For this exact reason there will be a generic table 'worker' which the tables with specific worker types 
will "extendt". Meaning, there will be a one-to-one relationship between theese tables. 
*/

DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS software_developer;
DROP TABLE IF EXISTS data_analyst;
DROP TABLE IF EXISTS intern;
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
    worker_type ENUM('MANAGER', 
					 'SOFTWARE_DEVELOPER', 
					 'DATA_ANALYST', 
					 'INTERN'),
    position VARCHAR(50),
    FOREIGN KEY (department_id) REFERENCES department(id) -- Many-to-one relationship
) AUTO_INCREMENT = 1;

CREATE TABLE manager (
    id INT PRIMARY KEY,
    progress_report VARCHAR(100),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE software_developer (
	id INT PRIMARY KEY,
    position_type ENUM('SENIOR', 'JUNIOR'),
    field VARCHAR(50),
	project VARCHAR(100),
    programming_language VARCHAR(100),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE data_analyst (
	id INT PRIMARY KEY,
    position_type ENUM('SENIOR', 'JUNIOR'),
	database_ VARCHAR(50),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE intern (
	id INT PRIMARY KEY,
	university VARCHAR(50),
    employed_until DATE,
    has_university_insurance BOOLEAN,
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE user (
	id INT PRIMARY KEY,
    type ENUM('MANAGER', 
			  'SOFTWARE_DEVELOPER', 
			  'DATA_ANALYST', 
			  'INTERN'),
    password VARCHAR(68),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE role (
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    role ENUM('ROLE_A', 'ROLE_B', 'ROLE_C', 'ROLE_D'),
    FOREIGN KEY (user_id) REFERENCES user(id) -- Many-to-one relationship
);