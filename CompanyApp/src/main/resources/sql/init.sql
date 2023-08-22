USE company;

/*
Initialize sample data for the test
*/

DROP PROCEDURE IF EXISTS create_users;
DELIMITER //

CREATE PROCEDURE create_users() 
BEGIN
	SET @user_id = 1;

	WHILE @user_id <= 109 DO
		IF @user_id = 6 THEN
			SET @user_id = 100;
        END IF;
		
        -- Password is '123'
		INSERT INTO user (id, password)
        VALUES (@user_id, '$2a$12$/xbS17qSTXILo1CZ5MpnBOSLjD/oEk.3CvUDT0IDvXGs.sV9JakvK');
        
        IF @user_id < 100 THEN
			INSERT INTO role (user_id, role)
            VALUES 
				(@user_id, "ROLE_EMPLOYEE"),
				(@user_id, "ROLE_MANAGER");
		ELSE
			INSERT INTO role (user_id, role)
            VALUES (@user_id, "ROLE_EMPLOYEE");
        END IF;
        
        SET @user_id = @user_id + 1;
	END WHILE;
END;
//

DELIMITER ;

CALL create_users();



INSERT INTO department (name)
VALUES
    ('Sales'),
    ('Marketing'),
    ('IT'),
    ('Finance'),
    ('HR');

INSERT INTO manager (department_id, first_name, last_name, employed_since, vacation, salary)
VALUES 
    (1, 'John', 'Doe', '2020-01-15', '2023-08-15', 75000.00),
    (2, 'Alice', 'Smith', '2018-05-10', '2023-07-20', 82000.00),
    (3, 'Michael', 'Johnson', '2016-03-01', '2023-09-30', 95000.00),
    (4, 'Emily', 'Williams', '2019-09-20', '2023-08-10', 78000.00),
    (5, 'David', 'Brown', '2017-11-08', '2023-10-05', 90000.00);
    
INSERT INTO employee (first_name, last_name, department_id, position, employed_since, vacation, salary)
VALUES
    ('Sarah', 'Johnson', 1, 'Sales Representative', '2019-05-15', '2023-07-15', 55000.00),
    ('Michael', 'Smith', 1, 'Sales Manager', '2018-03-10', '2023-08-20', 70000.00),
    ('Emily', 'Williams', 2, 'Marketing Specialist', '2020-02-01', '2023-09-10', 60000.00),
    ('David', 'Brown', 3, 'Software Engineer', '2017-09-20', '2023-08-05', 80000.00),
    ('Jessica', 'Miller', 4, 'Financial Analyst', '2019-12-08', '2023-10-15', 65000.00),
    ('John', 'Wilson', 1, 'Sales Representative', '2021-01-18', '2023-09-30', 58000.00),
    ('Alex', 'Martinez', 2, 'Marketing Coordinator', '2019-06-05', '2023-07-10', 54000.00),
    ('Daniel', 'Taylor', 3, 'Software Developer', '2018-07-10', '2023-08-25', 72000.00),
    ('Rachel', 'Jackson', 1, 'Sales Associate', '2020-03-15', '2023-06-15', 52000.00),
    ('Sophia', 'Anderson', 5, 'HR Manager', '2017-11-20', '2023-10-05', 75000.00);
    
-- Password is '123'
INSERT INTO user (id, password)
VALUES (0, '$2a$12$/xbS17qSTXILo1CZ5MpnBOSLjD/oEk.3CvUDT0IDvXGs.sV9JakvK');
   
INSERT INTO role (user_id, role)
VALUES 
	(0, 'ROLE_EMPLOYEE'),
	(0, 'ROLE_MANAGER'),
    (0, 'ROLE_ADMIN');