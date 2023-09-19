package com.company.CompanyApp.controller;

import com.company.CompanyApp.dto.WorkerData;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.service.IWorkerService;
import com.company.CompanyApp.service.WorkerManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Lazy
@Controller
@RequestMapping("/personal")
public class PersonalController <T extends Worker> {
    private final T loggedUser;
    private T viewedWorker;
    private List<WorkerData> workersFields;
    private final IWorkerService workerService;


    //CONSTRUCTORS
    public PersonalController(T loggedUser,
                              IWorkerService workerService) {

        this.loggedUser = loggedUser;
        this.workerService = workerService;
    }


    @GetMapping("")
    public String viewPersonalInfo(Model model) throws IllegalAccessException {
        viewedWorker = loggedUser;
        workersFields = WorkerManager.getWorkersFields(viewedWorker);

        model.addAttribute("fields", workersFields);

        return "app/personal-page";
    }


    @GetMapping("/{id}")
    public String viewPersonalInfo(Model model,
                                   @PathVariable int id)
                                   throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        viewedWorker = WorkerManager.getWorkerExtObject(workerService.getWorker(id));
        workersFields = WorkerManager.getWorkersFields(viewedWorker);

        model.addAttribute("fields", workersFields);

        return "app/personal-page";
    }


    @GetMapping("/edition")
    public String updatePersonalInfo(Model model) {
        model.addAttribute("fields", workersFields);

        return "app/edit-personal-page";
    }
}
