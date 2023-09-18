package com.company.CompanyApp.controller;

import com.company.CompanyApp.dto.WorkerData;
import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.enums.WorkerType;
import com.company.CompanyApp.service.IDepartmentService;
import com.company.CompanyApp.service.IWorkerService;
import com.company.CompanyApp.service.WorkerManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 */
@Lazy
@Controller
@RequestMapping("/home")
public class  HomeController <T extends Worker> {
    private final T loggedUser;
    private List<WorkerData> workersFields;
    private Department usersDepartment;
    private final IDepartmentService departmentService;
    private final IWorkerService workerService;


    public HomeController(Worker loggedUser,
                          IDepartmentService departmentService,
                          IWorkerService workerService)
                          throws NoSuchFieldException, ClassNotFoundException {

        this.loggedUser = WorkerManager.getWorkerExtObject(loggedUser);
        this.departmentService = departmentService;
        this.workerService = workerService;
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", loggedUser);
        return "app/home-page";
    }


    @GetMapping("/department")
    public String viewDepartmentInfo(Model model)  {
        if (usersDepartment == null) {
            usersDepartment = departmentService.getDepartment(loggedUser.getDepartmentId());
        }

        model.addAttribute("roles", UserContextConfig.getRoles());
        model.addAttribute("department", usersDepartment);

        return "app/department-page";
    }


    @GetMapping("/personal")
    public String viewPersonalInfo(Model model) throws IllegalAccessException {
        workersFields = WorkerManager.getWorkersFields(loggedUser);

        model.addAttribute("fields", workersFields);
        model.addAttribute("worker", loggedUser);

        return "app/personal-page";
    }


    @PostMapping("/department/worker")
    public String viewWorkerInfo(Model model,
                                 @RequestParam("id") int id,
                                 @RequestParam("workerType") WorkerType workerType)
                                 throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        T worker = WorkerManager.getWorkerExtObject(workerService.getWorker(id));
        workersFields = WorkerManager.getWorkersFields(worker);

        model.addAttribute("fields", workersFields);
        model.addAttribute("worker", worker);

        return "app/personal-page";
    }
}