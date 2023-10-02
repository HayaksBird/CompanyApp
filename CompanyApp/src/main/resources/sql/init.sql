USE company;

/*
Initialize sample data for the test
*/
INSERT INTO department (name)
VALUES
	('Administrator'),
    ('Analytics'),
    ('IT');
    
-- Add admin
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, worker_type, position)
	VALUES
		('Esmiralda', 'Nurieva', 1, 'enurieva71@gmail.com', '2022-01-01', '2024-01-01', 80000.75, 'ADMIN', 'Admin');
    
-- Insert data for the 'Analytics' department
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, worker_type, position)
VALUES
    ('Varga', 'Fjorsky', 2, 'enurieva71@gmail.com', '2022-01-01', '2024-01-01', 80000.75, 'MANAGER', 'Manager'),
    ('Jane', 'Smith', 2, 'enurieva71@gmail.com', '2022-01-15', '2024-01-15', 60000.00, 'DATA_ANALYST', 'Senior Business Analyst'),
    ('Michael', 'Brown', 2, 'senior2@gmail.com', '2022-02-01', '2024-02-01', 60000.00, 'DATA_ANALYST', 'Principal Data Analyst'),
    ('Sarah', 'Johnson', 2, 'junior1@gmail.com', '2022-02-15', '2024-02-15', 45000.35, 'DATA_ANALYST', 'Data Coordinator'),
    ('Robert', 'Wilson', 2, 'enurieva71@gmail.com', '2022-03-01', '2024-03-01', 45000.00, 'DATA_ANALYST', 'Research Assistant'),
    ('Emily', 'Davis', 2, 'intern1@gmail.com', '2023-06-01', '2024-08-01', 30000.00, 'INTERN', 'Data Technician');

-- Insert data for the 'IT' department
INSERT INTO worker (first_name, last_name, department_id, email, employed_since, vacation, salary, worker_type, position)
VALUES
    ('David', 'Miller', 3, 'enurieva71@gmail.com', '2022-01-01', '2024-01-01', 80000.00, 'MANAGER', 'Manager'),
    ('Olivia', 'Anderson', 3, 'enurieva71@gmail.com', '2022-01-15', '2024-01-15', 70000.00, 'SOFTWARE_DEVELOPER', 'Lead Developer'),
    ('William', 'Jones', 3, 'seniordev2@gmail.com', '2022-02-01', '2024-02-01', 70000.00, 'SOFTWARE_DEVELOPER', 'Senior Frontend Developer'),
    ('Elizabeth', 'Taylor', 3, 'juniord1@gmail.com', '2022-02-15', '2024-02-15', 55000.00, 'SOFTWARE_DEVELOPER', 'Entry-Level Software Developer'),
    ('Matthew', 'Clark', 3, 'enurieva71@gmail.com', '2022-03-01', '2024-03-01', 55000.00, 'SOFTWARE_DEVELOPER', 'Trainee Software Developer'),
    ('Sophia', 'Brown', 3, 'intern2@gmail.com', '2023-06-01', '2024-08-01', 30000.00, 'INTERN', 'Apprentice Developer');

-- Insert Admin
INSERT INTO admin (id)
VALUES(1);

-- Insert Managers
INSERT INTO manager (id)
VALUES (2), (8);

-- Insert Data Analysts
INSERT INTO data_analyst (id, position_type, database_)
VALUES
    (3, 'SENIOR', 'MySQL'),
    (4, 'SENIOR', 'SQL Server'),
    (5, 'JUNIOR', 'MySQL'),
    (6, 'JUNIOR', 'MySQL');

-- Insert Software Developers
INSERT INTO software_developer (id, position_type, field, project, programming_language)
VALUES
    (9, 'SENIOR', 'Security', 'Project A', 'Java'),
    (10, 'SENIOR', 'Front end', 'Project B', 'CSS'),
    (11, 'JUNIOR', 'Front end', 'Project C', 'JavaScript'),
    (12, 'JUNIOR', 'Back end', 'Project D', 'C#');
    
-- For Interns in the 'Analytics' department Insert Interns
INSERT INTO intern (id, university, employed_until, has_university_insurance)
VALUES
	(7, 'ABC University', '2024-08-01', TRUE),
    (13, 'XYZ University', '2024-08-01', FALSE);
    
-- Posts
INSERT INTO post (worker_id, title, content, author, post_date)
VALUES
	(1, 'Website Performance Update', "Hi Team,<br><br>I wanted to let you know that we're actively working on improving our website's performance. We're optimizing code, upgrading servers, and more to ensure a faster and smoother user experience.<br><br>Your feedback is essential, so please share any thoughts or ideas you have. Stay tuned for updates, and thank you for your support!", 'Esmiralda Abbasova', '2023-11-11'),
	(2, 'Party today!', "Dear colleagues, <br><br>We have a reason to celebrate, and we want you to be a part of it! Join us for a small office party today filled with delicious sandwiches and refreshing drinks.<br><br>Date: [2023-11-11] <br>Time: [16:00] <br>Location: [Canteen] <br><br>It is a great opportunity to unwind, chat with your coworkers, and enjoy some tasty treats. Whether you prefer classic ham and cheese or a vegetarian delight, we have got sandwiches to suit every palate. <br>And do not forget to quench your thirst with our selection of beverages. Let us take a break from our busy schedules to relax, connect, and have some fun together. We look forward to seeing you there! <br><br>If you have any dietary preferences or questions, please feel free to reach out to [Your Name] at [Your Contact Information]. See you at the party!", 'Varga Fjorsky', '2023-11-11');