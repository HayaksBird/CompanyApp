package com.company.CompanyApp.controller;

import com.company.CompanyApp.dto.WorkerData;
import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.entity.worker.DataAnalyst;
import com.company.CompanyApp.service.IDepartmentService;
import com.company.CompanyApp.service.IWorkerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Lazy
@Controller
@RequestMapping("/data_analyst")
public class DataAnalystController {
    private final DataAnalyst dataAnalyst;
    private Department usersDepartment;
    private List<WorkerData> workersFields;
    private final IDepartmentService departmentService;
    private final IWorkerService workerService;


    //CONSTRUCTORS
    public DataAnalystController(DataAnalyst dataAnalyst,
                                 IDepartmentService departmentService,
                                 IWorkerService workerService) {

        this.dataAnalyst = dataAnalyst;
        this.departmentService = departmentService;
        this.workerService = workerService;
    }


    @GetMapping("/department")
    public String viewDepartmentInfo(Model model) {
        if (usersDepartment == null) {
            usersDepartment = departmentService.getDepartment(dataAnalyst.getDepartmentId());
        }

        model.addAttribute("roles", UserContextConfig.getRoles());
        model.addAttribute("department", usersDepartment);

        return "app/department-page";
    }


    @GetMapping("/personal")
    public String viewPersonalInfo(Model model) throws IllegalAccessException {
        if (workersFields == null) {
            workersFields = UserContextConfig.getWorkersFields(dataAnalyst);
        }

        model.addAttribute("fields", workersFields);
        model.addAttribute("worker", dataAnalyst);

        return "app/personal-page";
    }
}
