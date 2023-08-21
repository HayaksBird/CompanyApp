USE company;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS department;



CREATE TABLE department (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    employee_count INT,
    min_budget INT
);

CREATE TABLE manager (
    department_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    employed_since DATE,
    vacation DATE,
    salary DECIMAL(10, 2),
    FOREIGN KEY (department_id) REFERENCES department(id)
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
    FOREIGN KEY (department_id) REFERENCES department(id)
);