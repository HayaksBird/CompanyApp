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
    private final T loggedUser;
    private final BindingService<T> bindingService;
    private T viewedWorker;
    private List<WorkerData> workersFields;
    private final IWorkerService workerService;


    //CONSTRUCTORS
    public PersonalController(T loggedUser,
                              IWorkerService workerService,
                              BindingService<T> bindingService) {

        this.loggedUser = loggedUser;
        this.workerService = workerService;
        this.bindingService = bindingService;
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
                                   @PathVariable
                                   int id)
                                   throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, WorkerNotFoundException {

        viewedWorker = WorkerManager.getWorkerExtObject(workerService.getWorker(id));
        workersFields = WorkerManager.getWorkersFields(viewedWorker);

        model.addAttribute("fields", workersFields);

        return "app/personal-page";
    }


    @GetMapping("/edition")
    public String updatePersonalInfo(Model model) {
        model.addAttribute("data", new WorkerDataContainer(workersFields));

        return "app/edit-personal-page";
    }


    @PostMapping("/edition")
    public String updatePersonalInfo(@ModelAttribute
                                     WorkerDataContainer data,
                                     Model model) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        T updatedWorker;
        List<String> errorMessages;

        updatedWorker = (T) viewedWorker.getClass().getDeclaredConstructor().newInstance();
        bindingService.bindToWorkerEntity(data.getWorkersData(), updatedWorker);
        errorMessages = bindingService.getErrorMessages();

        if (errorMessages.isEmpty()) {
            workerService.updateWorker(updatedWorker);

            viewedWorker = updatedWorker;
        } else {
            data.setErrorMessages(errorMessages);
            model.addAttribute("data", data);

            return "app/edit-personal-page";
        }

        return null;
    }
}
