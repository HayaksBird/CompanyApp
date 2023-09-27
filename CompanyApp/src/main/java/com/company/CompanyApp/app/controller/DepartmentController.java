package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.Department;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.service.IDepartmentService;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.exception.DepartmentNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Lazy
@Controller
@RequestMapping("/department")
public class DepartmentController <T extends Worker> {
    private final T loggedUser;
    private Department usersDepartment;
    private final IDepartmentService departmentService;
    private final IHierarchyService hierarchyManagementService;


    //CONSTRUCTORS
    public DepartmentController(T loggedUser,
                                IDepartmentService departmentService,
                                IHierarchyService hierarchyManagementService) {

        this.loggedUser = loggedUser;
        this.departmentService = departmentService;
        this.hierarchyManagementService = hierarchyManagementService;
    }


    @GetMapping("")
    public String viewDepartmentInfo(Model model) throws DepartmentNotFoundException {
        usersDepartment = departmentService.getDepartmentWithWorkers(loggedUser.getDepartmentId());

        model.addAttribute("roles", hierarchyManagementService.getLoggedUsersRoles());
        model.addAttribute("department", usersDepartment);

        return "app/department-page";
    }
}
