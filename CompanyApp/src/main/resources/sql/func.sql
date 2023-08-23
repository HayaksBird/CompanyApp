USE company;

/*
This query script adds a layer of management over the department table.
Primarly it focuses on updating the fields that are dependent on external factors.
In addition to that, it validates the user.
*/

DROP PROCEDURE IF EXISTS update_min_budget;
DROP PROCEDURE IF EXISTS update_employee_count;
DELIMITER //

/*
These procedures depending on the input parameters update the dependent fields of 
the department table: min_budget, employee_count
*/
CREATE PROCEDURE update_min_budget(old_salary INT, new_salary INT, department_id INT) 
BEGIN
	UPDATE department
    SET min_budget = min_budget - old_salary + new_salary
    WHERE id = department_id;
END;
//

CREATE PROCEDURE update_employee_count(toDelete BOOLEAN, department_id INT)
BEGIN
	DECLARE update_value INT;
    
	IF toDelete IS TRUE THEN
		SET update_value = -1;
	ELSE 
		SET update_value = 1;
    END IF;
    
    UPDATE department
    SET employee_count = employee_count + update_value
    WHERE id = department_id;
END;
//



/*
This trigger initializes the dependent fields of the department table to 0.
*/
CREATE TRIGGER set_new_department
BEFORE INSERT ON department
FOR EACH ROW
BEGIN
    SET NEW.employee_count = 0;
    SET NEW.min_budget = 0;
END;
//



/*
These triggers focus on updating the department table based on what changes were
done on the employee table.
*/
CREATE TRIGGER update_department_info_EC
AFTER INSERT ON employee
FOR EACH ROW
BEGIN
	CALL update_employee_count(FALSE, NEW.department_id);
    CALL update_min_budget(0, NEW.salary, NEW.department_id);
END;
//

CREATE TRIGGER update_department_info_ED
AFTER DELETE ON employee
FOR EACH ROW
BEGIN
	CALL update_employee_count(TRUE, OLD.department_id);
    CALL update_min_budget(OLD.salary, 0, OLD.department_id);
END;
//

CREATE TRIGGER update_department_info_EU
AFTER UPDATE ON employee
FOR EACH ROW
BEGIN
    CALL update_min_budget(OLD.salary, NEW.salary, NEW.department_id);
END;
//



/*
These triggers focus on updating the department table based on what changes were
done on the manager table.
*/
CREATE TRIGGER update_department_info_MC
AFTER INSERT ON manager
FOR EACH ROW
BEGIN
    CALL update_min_budget(0, NEW.salary, NEW.department_id);
END;
//

CREATE TRIGGER update_department_info_MD
AFTER DELETE ON manager
FOR EACH ROW
BEGIN
    CALL update_min_budget(OLD.salary, 0, OLD.department_id);
END;
//

CREATE TRIGGER update_department_info_MU
AFTER UPDATE ON manager
FOR EACH ROW
BEGIN
    CALL update_min_budget(OLD.salary, NEW.salary, NEW.department_id);
END;
//



/*
Make sure that the person already exists in the database, so that the 
user could be inserted.
A person with id 0 is the admin.
*/
CREATE TRIGGER is_user_valid
BEFORE INSERT ON user
FOR EACH ROW
BEGIN
	DECLARE user_id INT;
    
    SELECT id INTO user_id FROM employee WHERE new.id = employee.id;
    
    IF user_id IS NULL THEN
		SELECT department_id INTO user_id FROM manager WHERE new.id = manager.department_id;
    END IF;
    
    IF user_id IS NULL AND user_id != 0 THEN
		SIGNAL SQLSTATE '45000';
    END IF;
END;
//

DELIMITER ;