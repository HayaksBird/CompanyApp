USE company;

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
These triggers focus on updating the department table based on what changes were
done on the worker table.
*/
CREATE TRIGGER set_related_data_create
AFTER INSERT ON worker
FOR EACH ROW
BEGIN
    CALL update_employee_count(FALSE, NEW.department_id);
    CALL update_min_budget(0, NEW.salary, NEW.department_id);
END;
//

CREATE TRIGGER set_related_data_delete
AFTER DELETE ON worker
FOR EACH ROW
BEGIN
	CALL update_employee_count(TRUE, OLD.department_id);
    CALL update_min_budget(OLD.salary, 0, OLD.department_id);
END;
//

CREATE TRIGGER set_related_data_update
AFTER UPDATE ON worker
FOR EACH ROW
BEGIN
    CALL update_min_budget(OLD.salary, NEW.salary, NEW.department_id);
END;
//

DELIMITER ;