USE company;

/*
Initialize sample data for the test
*/
INSERT INTO department (name)
VALUES
    ('Analytics'),
    ('IT');
    
-- Insert data for the 'Analytics' department
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, worker_type, position)
VALUES
    ('Esmiralda', 'Nurieva', 1, 'enurieva71@gmail.com', '2022-01-01', '2023-01-01', 80000.00, 'MANAGER', 'Manager'),
    ('Jane', 'Smith', 1, 'enurieva71@gmail.com', '2022-01-15', '2023-01-15', 60000.00, 'DATA_ANALYST', 'Senior Business Analyst'),
    ('Michael', 'Brown', 1, 'senior2@gmail.com', '2022-02-01', '2023-02-01', 60000.00, 'DATA_ANALYST', 'Principal Data Analyst'),
    ('Sarah', 'Johnson', 1, 'junior1@gmail.com', '2022-02-15', '2023-02-15', 45000.00, 'DATA_ANALYST', 'Data Coordinator'),
    ('Robert', 'Wilson', 1, 'enurieva71@gmail.com', '2022-03-01', '2023-03-01', 45000.00, 'DATA_ANALYST', 'Research Assistant'),
    ('Emily', 'Davis', 1, 'intern1@gmail.com', '2023-06-01', '2023-08-01', 30000.00, 'INTERN', 'Data Technician');

-- Insert data for the 'IT' department
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, worker_type, position)
VALUES
    ('David', 'Miller', 2, 'manager2@gmail.com', '2022-01-01', '2023-01-01', 80000.00, 'MANAGER', 'Manager'),
    ('Olivia', 'Anderson', 2, 'seniordev1@gmail.com', '2022-01-15', '2023-01-15', 70000.00, 'SOFTWARE_DEVELOPER', 'Lead Developer'),
    ('William', 'Jones', 2, 'seniordev2@gmail.com', '2022-02-01', '2023-02-01', 70000.00, 'SOFTWARE_DEVELOPER', 'Senior Frontend Developer'),
    ('Elizabeth', 'Taylor', 2, 'juniord1@gmail.com', '2022-02-15', '2023-02-15', 55000.00, 'SOFTWARE_DEVELOPER', 'Entry-Level Software Developer'),
    ('Matthew', 'Clark', 2, 'juniord2@gmail.com', '2022-03-01', '2023-03-01', 55000.00, 'SOFTWARE_DEVELOPER', 'Trainee Software Developer'),
    ('Sophia', 'Brown', 2, 'intern2@gmail.com', '2023-06-01', '2023-08-01', 30000.00, 'INTERN', 'Apprentice Developer');

-- Insert Managers
INSERT INTO manager (id)
VALUES (1), (7);

-- Insert Data Analysts
INSERT INTO data_analyst (id, position_type, database_)
VALUES
    (2, 'SENIOR', 'MySQL'),
    (3, 'SENIOR', 'SQL Server'),
    (4, 'JUNIOR', 'MySQL'),
    (5, 'JUNIOR', 'MySQL');

-- Insert Software Developers
INSERT INTO software_developer (id, position_type, field, project, programming_language)
VALUES
    (8, 'SENIOR', 'Security', 'Project A', 'Java'),
    (9, 'SENIOR', 'Front end', 'Project B', 'CSS'),
    (10, 'JUNIOR', 'Front end', 'Project C', 'JavaScript'),
    (11, 'JUNIOR', 'Back end', 'Project D', 'C#');
    
-- For Interns in the 'Analytics' department Insert Interns
INSERT INTO intern (id, university, employed_until, has_university_insurance)
VALUES
	(6, 'ABC University', '2023-08-01', TRUE),
    (12, 'XYZ University', '2023-08-01', FALSE);