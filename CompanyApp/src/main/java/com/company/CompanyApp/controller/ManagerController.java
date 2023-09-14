package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.entity.worker.Manager;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.service.IDepartmentService;
import com.company.CompanyApp.service.IWorkerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Lazy
@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final Manager manager;
    private Department usersDepartment;
    private final IDepartmentService departmentService;
    private final IWorkerService workerService;


    //CONSTRUCTORS
    public ManagerController(Worker loggedUser,
                             IDepartmentService departmentService,
                             IWorkerService workerService) {

        manager = (Manager) loggedUser;

        this.departmentService = departmentService;
        this.workerService = workerService;
    }


    @GetMapping("/department")
    public String viewDepartmentInfo(Model model) {
        if (usersDepartment == null) {
            usersDepartment = departmentService.getDepartment(manager.getDepartmentId());
        }

        model.addAttribute("roles", UserContextConfig.getRoles());
        model.addAttribute("department", usersDepartment);

        return "app/department-page";
    }


    @GetMapping("/personal")
    public String viewPersonalInfo(Model model) {
        model.addAttribute("worker", manager);

        return "app/personal-page";
    }
}
