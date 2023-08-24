USE company;

/*
Initialize sample data for the test
*/
INSERT INTO department (name)
VALUES
    ('Sales'),
    ('Marketing'),
    ('IT'),
    ('Finance'),
    ('HR');

INSERT INTO manager (department_id, first_name, last_name, email, employed_since, vacation, salary)
VALUES 
    (1, 'Esmiralda', 'Nurieva', 'enurieva71@gmail.com', '2020-01-15', '2023-08-15', 75000.00),
    (2, 'Alice', 'Smith', 'alice.smith@gmail.com', '2018-05-10', '2023-07-20', 82000.00),
    (3, 'Michael', 'Johnson', 'michael.johnson@gmail.com', '2016-03-01', '2023-09-30', 95000.00),
    (4, 'Emily', 'Williams', 'emily.williams@gmail.com', '2019-09-20', '2023-08-10', 78000.00),
    (5, 'David', 'Brown', 'david.brown@gmail.com', '2017-11-08', '2023-10-05', 90000.00);
    
INSERT INTO employee (first_name, last_name, department_id, position, email, employed_since, vacation, salary)
VALUES
    ('Sarah', 'Johnson', 1, 'Sales Representative', 'sarah.johnson@gmail.com', '2019-05-15', '2023-07-15', 55000.00),
    ('Michael', 'Smith', 1, 'Sales Manager', 'michael.smith@gmail.com', '2018-03-10', '2023-08-20', 70000.00),
    ('Emily', 'Williams', 2, 'Marketing Specialist', 'emily.williams@gmail.com', '2020-02-01', '2023-09-10', 60000.00),
    ('David', 'Brown', 3, 'Software Engineer', 'david.brown@gmail.com', '2017-09-20', '2023-08-05', 80000.00),
    ('Jessica', 'Miller', 4, 'Financial Analyst', 'jessica.miller@gmail.com', '2019-12-08', '2023-10-15', 65000.00),
    ('John', 'Wilson', 1, 'Sales Representative', 'john.wilson@gmail.com', '2021-01-18', '2023-09-30', 58000.00),
    ('Alex', 'Martinez', 2, 'Marketing Coordinator', 'alex.martinez@gmail.com', '2019-06-05', '2023-07-10', 54000.00),
    ('Daniel', 'Taylor', 3, 'Software Developer', 'daniel.taylor@gmail.com', '2018-07-10', '2023-08-25', 72000.00),
    ('Rachel', 'Jackson', 1, 'Sales Associate', 'rachel.jackson@gmail.com', '2020-03-15', '2023-06-15', 52000.00),
    ('Sophia', 'Anderson', 5, 'HR Manager', 'sophia.anderson@gmail.com', '2017-11-20', '2023-10-05', 75000.00);
    
-- Password is '123'
INSERT INTO user (id, password)
VALUES (0, '$2a$12$/xbS17qSTXILo1CZ5MpnBOSLjD/oEk.3CvUDT0IDvXGs.sV9JakvK');
    
INSERT INTO role (user_id, role)
VALUES 
	(0, 'ROLE_EMPLOYEE'),
	(0, 'ROLE_MANAGER'),
    (0, 'ROLE_ADMIN');