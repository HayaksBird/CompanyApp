package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.enums.WorkerType;
import com.company.CompanyApp.service.IDepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;

@Lazy
@Controller
@RequestMapping("/home")
public class HomeController {
    private final WorkerType type;
    private Department usersDepartment;
    private final Worker loggedUser;
    private final IDepartmentService departmentService;


    public HomeController(Worker loggedUser,
                          IDepartmentService departmentService) {

        this.loggedUser = loggedUser;
        this.departmentService = departmentService;

        type = loggedUser.getWorkerType();
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", loggedUser);
        return "app/home-page";
    }


    @GetMapping("/personal")
    public String viewUserInfo() {
        return "Shit";
    }


    @GetMapping("/department")
    public String viewDepartmentInfo(Model model) {
        if (usersDepartment == null) {
            usersDepartment = departmentService.getDepartment(loggedUser.getDepartmentId());
        }

        model.addAttribute("type", type);
        model.addAttribute("department", usersDepartment);

        return "app/department-page";
    }
}
