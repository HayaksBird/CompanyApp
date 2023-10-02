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
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS software_developer;
DROP TABLE IF EXISTS data_analyst;
DROP TABLE IF EXISTS intern;
DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS worker;
DROP TABLE IF EXISTS department;



CREATE TABLE department (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name TINYTEXT,
    employee_count INT DEFAULT 0,
    min_budget INT DEFAULT 0
) AUTO_INCREMENT = 1;

CREATE TABLE worker (
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name TINYTEXT,
    last_name TINYTEXT,
    department_id INT,
    email TINYTEXT CHECK (email LIKE '%@gmail.com'),
    employed_since DATE,
    vacation DATE,
    salary DECIMAL(10, 2),
    worker_type ENUM('MANAGER', 
					 'SOFTWARE_DEVELOPER', 
					 'DATA_ANALYST', 
					 'INTERN', 
                     'ADMIN'),
    position TINYTEXT,
    FOREIGN KEY (department_id) REFERENCES department(id) -- Many-to-one relationship
) AUTO_INCREMENT = 1;

CREATE TABLE admin (
	id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE manager (
    id INT PRIMARY KEY,
    progress_report TINYTEXT,
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE software_developer (
	id INT PRIMARY KEY,
    position_type ENUM('SENIOR', 'JUNIOR'),
    field TINYTEXT,
	project TINYTEXT,
    programming_language TINYTEXT,
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE data_analyst (
	id INT PRIMARY KEY,
    position_type ENUM('SENIOR', 'JUNIOR'),
	database_ TINYTEXT,
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE intern (
	id INT PRIMARY KEY,
	university TINYTEXT,
    employed_until DATE,
    has_university_insurance BOOLEAN,
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE post (
	id INT PRIMARY KEY AUTO_INCREMENT,
    post_date DATE,
    worker_id INT,
    title TINYTEXT,
    content TEXT,
    author TINYTEXT,
    FOREIGN KEY (worker_id) REFERENCES worker(id)	-- Many-to-one relationship
);

CREATE TABLE user (
	id INT PRIMARY KEY,
    type ENUM('MANAGER', 
			  'SOFTWARE_DEVELOPER', 
			  'DATA_ANALYST', 
			  'INTERN',
              'ADMIN'),
    password VARCHAR(68),
    FOREIGN KEY (id) REFERENCES worker(id)	-- One-to-one relationship
);

CREATE TABLE role (
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    role ENUM('ROLE_A', 'ROLE_B', 'ROLE_C', 'ROLE_D'),
    FOREIGN KEY (user_id) REFERENCES user(id) -- Many-to-one relationship
);