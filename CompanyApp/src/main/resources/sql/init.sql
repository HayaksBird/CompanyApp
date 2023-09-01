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
    
-- Insert 5 managers
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, type)
VALUES
    ('John', 'Doe', 1, 'john.doe@gmail.com', '2022-01-01', '2022-07-01', 75000.00, 'manager'),
    ('Jane', 'Smith', 2, 'jane.smith@gmail.com', '2021-02-15', '2022-08-15', 82000.00, 'manager'),
    ('Michael', 'Johnson', 3, 'michael.johnson@gmail.com', '2020-03-20', '2022-09-30', 90000.00, 'manager'),
    ('Emily', 'Williams', 4, 'emily.williams@gmail.com', '2019-04-10', '2022-10-10', 98000.00, 'manager'),
    ('Daniel', 'Brown', 5, 'daniel.brown@gmail.com', '2018-05-05', '2022-11-20', 105000.00, 'manager');
    
-- Insert 15 employees
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, type)
VALUES
    ('Sarah', 'Clark', 1, 'sarah.clark@gmail.com', '2020-06-15', '2022-06-15', 55000.00, 'employee'),
    ('Matthew', 'Lee', 2, 'matthew.lee@gmail.com', '2021-01-10', '2022-09-10', 62000.00, 'employee'),
    ('Olivia', 'Garcia', 3, 'olivia.garcia@gmail.com', '2019-03-25', '2022-08-05', 68000.00, 'employee'),
    ('William', 'Martinez', 4, 'william.martinez@gmail.com', '2018-07-20', '2022-10-20', 72000.00, 'employee'),
    ('Ava', 'Rodriguez', 5, 'ava.rodriguez@gmail.com', '2022-02-01', '2022-12-01', 60000.00, 'employee'),
    ('James', 'Hernandez', 1, 'james.hernandez@gmail.com', '2022-01-15', '2022-08-15', 54000.00, 'employee'),
    ('Sophia', 'Lopez', 2, 'sophia.lopez@gmail.com', '2021-03-20', '2022-09-30', 61000.00, 'employee'),
    ('Benjamin', 'Perez', 3, 'benjamin.perez@gmail.com', '2020-05-10', '2022-10-10', 67000.00, 'employee'),
    ('Emma', 'Gonzalez', 4, 'emma.gonzalez@gmail.com', '2019-08-05', '2022-11-20', 71000.00, 'employee'),
    ('Alexander', 'Rivera', 5, 'alexander.rivera@gmail.com', '2018-10-15', '2022-06-15', 59000.00, 'employee'),
    ('Liam', 'Diaz', 1, 'liam.diaz@gmail.com', '2020-02-10', '2022-09-10', 56000.00, 'employee'),
    ('Mia', 'Wright', 2, 'mia.wright@gmail.com', '2021-04-25', '2022-08-05', 63000.00, 'employee'),
    ('Ethan', 'Reyes', 3, 'ethan.reyes@gmail.com', '2019-06-20', '2022-10-20', 69000.00, 'employee'),
    ('Isabella', 'Martin', 4, 'isabella.martin@gmail.com', '2018-09-15', '2022-12-01', 73000.00, 'employee'),
    ('Noah', 'Jackson', 5, 'noah.jackson@gmail.com', '2022-03-01', '2022-08-15', 57000.00, 'employee');

-- Insert new managers with IDs between 1 and 5
INSERT INTO manager (id)
VALUES (1), (2), (3), (4), (5);

-- Insert new employees with IDs between 6 and 20
INSERT INTO employee (id, position)
VALUES
    (6, 'Sales Associate'),
    (7, 'Marketing Specialist'),
    (8, 'Software Engineer'),
    (9, 'Financial Analyst'),
    (10, 'HR Coordinator'),
    (11, 'Sales Representative'),
    (12, 'Software Developer'),
    (13, 'Marketing Coordinator'),
    (14, 'Financial Planner'),
    (15, 'HR Assistant'),
    (16, 'Sales Manager'),
    (17, 'Software Engineer'),
    (18, 'Marketing Manager'),
    (19, 'Financial Analyst'),
    (20, 'HR Friendly Guy');