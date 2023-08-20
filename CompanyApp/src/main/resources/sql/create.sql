USE company;

DROP TABLE IF EXISTS employer;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS manager;



CREATE TABLE manager (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    employed_since DATE,
    vacation DATE,
    salary DECIMAL(10, 2)
);

CREATE TABLE department (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    employer_count INT,
    min_budget INT,
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE employer (
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