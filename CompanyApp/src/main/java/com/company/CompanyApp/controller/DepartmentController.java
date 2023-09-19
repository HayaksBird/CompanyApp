package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.service.IDepartmentService;
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


    //CONSTRUCTORS
    public DepartmentController(T loggedUser,
                                IDepartmentService departmentService) {

        this.loggedUser = loggedUser;
        this.departmentService = departmentService;
    }


    @GetMapping("")
    public String viewDepartmentInfo(Model model)  {
        if (usersDepartment == null) {
            usersDepartment = departmentService.getDepartment(loggedUser.getDepartmentId());
        }

        model.addAttribute("roles", UserContextConfig.getRoles());
        model.addAttribute("department", usersDepartment);

        return "app/department-page";
    }
}
