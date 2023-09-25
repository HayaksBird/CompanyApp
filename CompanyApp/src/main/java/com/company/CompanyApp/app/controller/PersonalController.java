package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.dto.WorkerData;
import com.company.CompanyApp.app.dto.WorkerDataContainer;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.validation.service.BindingService;
import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.app.service.WorkerManager;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Lazy
@Controller
@RequestMapping("/personal")
public class PersonalController <T extends Worker> {
    private final BindingService bindingService;
    private final WorkerManager workerManager;
    private final IWorkerService workerService;
    private final T loggedUser;
    private T viewedWorker;
    private final String templateDir;
    private List<WorkerData> workersFields;



    //CONSTRUCTORS
    public PersonalController(T loggedUser,
                              IWorkerService workerService,
                              BindingService bindingService,
                              WorkerManager workerManager) {

        templateDir = "app";
        this.loggedUser = loggedUser;

        this.workerManager = workerManager;
        this.workerService = workerService;
        this.bindingService = bindingService;
    }


    @GetMapping("")
    public String viewPersonalInfo(Model model) throws IllegalAccessException {
        viewedWorker = loggedUser;
        workersFields = workerManager.getWorkersFields(viewedWorker);

        model.addAttribute("fields", workersFields);

        return String.format("%s/personal-page", templateDir);
    }


    @GetMapping("/{id}")
    public String viewPersonalInfo(Model model,
                                   @PathVariable
                                   int id)
                                   throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, WorkerNotFoundException {

        viewedWorker = workerManager.getWorkerExtObject(workerService.getWorker(id));
        workersFields = workerManager.getWorkersFields(viewedWorker);

        model.addAttribute("fields", workersFields);

        return String.format("%s/personal-page", templateDir);
    }


    @GetMapping("/edition")
    public String updatePersonalInfo(Model model) {
        model.addAttribute("data", new WorkerDataContainer(workersFields));

        return String.format("%s/edit-personal-page", templateDir);
    }


    @PostMapping("/edition")
    public String updatePersonalInfo(@ModelAttribute
                                     WorkerDataContainer data,
                                     Model model)
                                     throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        T updatedWorker;
        List<String> errorMessages;

        updatedWorker = (T) viewedWorker.getClass().getDeclaredConstructor().newInstance();
        updatedWorker.setWorkerType(viewedWorker.getWorkerType());

        bindingService.bindToWorkerEntity(data.getWorkersData(), updatedWorker);
        errorMessages = bindingService.getErrorMessages();

        if (errorMessages.isEmpty()) {
            workerService.updateWorker(updatedWorker);

            //Make sure to update the logged user if necessary
            if (viewedWorker == loggedUser) {
                workerManager.equalize(loggedUser.getClass(), loggedUser, updatedWorker);
            }

            data.setAdded(true);
        } else data.setErrorMessages(errorMessages);

        model.addAttribute("data", data);

        return String.format("%s/edit-personal-page", templateDir);
    }
}
