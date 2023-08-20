USE company;



DELIMITER //

CREATE TRIGGER set_new_department
BEFORE INSERT ON department
FOR EACH ROW
BEGIN
	DECLARE managerSalary DECIMAL(10, 2);
    
    SELECT salary INTO managerSalary FROM manager WHERE NEW.manager_id = manager.id;
    SET NEW.employer_count = 0;
    SET NEW.min_budget = managerSalary;
END;
//

CREATE TRIGGER update_department_info
AFTER INSERT ON employer
FOR EACH ROW
BEGIN
	UPDATE department
	SET employer_count = employer_count + 1
	WHERE id = NEW.department_id;
    
    UPDATE department
    SET min_budget = min_budget + NEW.salary
    WHERE id = NEW.department_id;
END;
//

DELIMITER ;