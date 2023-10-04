package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.dto.DepartmentContainer;
import com.company.CompanyApp.app.entity.Department;
import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.service.IDepartmentService;
import com.company.CompanyApp.exception.ItemNotFoundException;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.validation.dto.ModelData;
import com.company.CompanyApp.validation.dto.ModelDataContainer;
import com.company.CompanyApp.validation.service.BindingService;
import com.company.CompanyApp.validation.service.ModelDataService;
import com.company.CompanyApp.validation.service.ValidationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 *
 * This controller handles the department view and the department edition/creation/deletion.
 * Note that if the department is deleted, then so are all of its workers.
 */
@Lazy
@Controller
@RequestMapping("/department")
public class DepartmentController <T extends Worker> {
    private final T loggedUser;
    private Department department;
    private Department tempDepartment;
    private final IDepartmentService departmentService;
    private final IHierarchyService hierarchyManagementService;
    private final BindingService bindingService;
    private final ModelDataService modelDataService;
    private final ValidationService validationService;
    private final String templateDir;
    private List<ModelData> departmentsFields;


    //CONSTRUCTORS
    public DepartmentController(T loggedUser,
                                IDepartmentService departmentService,
                                IHierarchyService hierarchyManagementService,
                                BindingService bindingService,
                                ModelDataService modelDataService,
                            ValidationService validationService) {

        templateDir = "app/department";
        this.loggedUser = loggedUser;

        this.departmentService = departmentService;
        this.hierarchyManagementService = hierarchyManagementService;
        this.bindingService = bindingService;
        this.modelDataService = modelDataService;
        this.validationService = validationService;
    }


    //VIEW DEPARTMENT
    /**
     * Show department info for the logged-in user.
     */
    @GetMapping("")
    public String viewDepartment(Model model)
                                 throws ItemNotFoundException {

        department = departmentService.getDepartmentWithWorkers(loggedUser.getDepartmentId());

        model.addAttribute("roles", hierarchyManagementService.getLoggedUsersRoles());
        model.addAttribute("data", new DepartmentContainer(department));

        return String.format("%s/department-page", templateDir);
    }


    /**
     * Search other department info (for admin only).
     */
    @GetMapping("/other")
    public String viewDepartment(@ModelAttribute
                                 DepartmentContainer data,
                                 Model model) {

        data.setErrorMessages(validationService.validate(data));

        try {
            if (data.getErrorMessages().isEmpty()) {
                department = departmentService.getDepartmentWithWorkers(Integer.parseInt(data.getDepartmentId()));
            }
        } catch (ItemNotFoundException ex) {
            data.setErrorMessage("Department does not exist");
        }

        data.setDepartment(department);
        model.addAttribute("roles", hierarchyManagementService.getLoggedUsersRoles());
        model.addAttribute("data", data);

        return String.format("%s/department-page", templateDir);
    }


    //DELETE DEPARTMENT
    /**
     * Delete department.
     */
    @DeleteMapping("/deletion")
    public String deleteDepartment(@ModelAttribute
                                   DepartmentContainer data,
                                   Model model) {

        departmentService.delete(department);

        data.setDepartment(department);
        data.setOperationSuccessful(true);

        model.addAttribute("roles", hierarchyManagementService.getLoggedUsersRoles());
        model.addAttribute("data", data);

        return String.format("%s/department-page", templateDir);
    }


    //UPDATE DEPARTMENT
    /**
     * Prepare the department update template for the user.
     */
    @GetMapping("/edition")
    public String editDepartment(@ModelAttribute
                                 DepartmentContainer data,
                                 Model model) throws IllegalAccessException {

        tempDepartment = null;

        departmentsFields = modelDataService.getModelsFields(department);

        data.setModelData(departmentsFields);
        model.addAttribute("data", data);

        return String.format("%s/edit-department-page", templateDir);
    }


    /**
     * Process the department update template.
     * Create an object model, which will be bound with the data from the form.
     * Note that the static data is automatically set.
     * If no errors to be found during the binding, then we persist the worker.
     */
    @PutMapping("/edition")
    public String processUpdate(@ModelAttribute
                                DepartmentContainer data,
                                Model model) throws IllegalAccessException {

        tempDepartment = new Department();
        //Set static data
        tempDepartment.setId(department.getId());
        tempDepartment.setEmployeeCount(department.getEmployeeCount());
        tempDepartment.setMinBudget(department.getMinBudget());
        tempDepartment.setWorkers(department.getWorkers());

        saveDepartment(data);

        model.addAttribute("data", data);

        return String.format("%s/edit-department-page", templateDir);
    }


    //CREATE DEPARTMENT
    /**
     * Prepare the department creation template for the user.
     */
    @GetMapping("/creation")
    public String createDepartment(Model model) throws IllegalAccessException {
        tempDepartment = null;

        departmentsFields = modelDataService.getModelsFields(new Department());

        model.addAttribute("data", new DepartmentContainer(departmentsFields));

        return String.format("%s/create-department-page", templateDir);
    }


    /**
     * Process the department creation template.
     * Create an object model, which will be bound with the data from the form.
     * If no errors to be found during the binding, then we persist the worker.
     */
    @PostMapping("/creation")
    public String processCreate(@ModelAttribute
                                DepartmentContainer data,
                                Model model) throws IllegalAccessException {

        tempDepartment = new Department();

        saveDepartment(data);

        model.addAttribute("data", data);

        return String.format("%s/create-department-page", templateDir);
    }


    /**
     * Attempt to save a department in to the database (update/create).
     * If the operation is successful, then the container's status message
     * is set accordingly. However, if failed, the error list is provided to the
     * container.
     */
    private void saveDepartment(ModelDataContainer data) throws IllegalAccessException {
        List<String> errorMessages = bindingService.bindToModelEntity(data.getModelData(), tempDepartment);

        if (errorMessages.isEmpty()) {
            departmentService.save(tempDepartment);
            data.setOperationSuccessful(true);
        } else data.setErrorMessages(errorMessages);
    }
}