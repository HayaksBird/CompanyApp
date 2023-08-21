USE company;



DROP PROCEDURE IF EXISTS update_min_budget;
DROP PROCEDURE IF EXISTS update_employee_count;
DELIMITER //

/*

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

DELIMITER ;