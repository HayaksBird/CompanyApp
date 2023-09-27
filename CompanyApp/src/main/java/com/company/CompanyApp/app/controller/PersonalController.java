package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.dto.WorkerData;
import com.company.CompanyApp.app.dto.WorkerDataContainer;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.validation.service.BindingService;
import com.company.CompanyApp.app.service.IWorkerService;
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
    private final IHierarchyService hierarchyService;
    private final IWorkerService workerService;
    private final String templateDir;
    private final T loggedUser;
    private T viewedWorker;
    private T tempWorker;
    private List<WorkerData> workersFields;


    //CONSTRUCTORS
    public PersonalController(T loggedUser,
                              IWorkerService workerService,
                              BindingService bindingService,
                              IHierarchyService hierarchyService) {

        templateDir = "app";
        this.loggedUser = loggedUser;

        this.hierarchyService = hierarchyService;
        this.workerService = workerService;
        this.bindingService = bindingService;
    }


    //VIEW PERSONAL PAGE
    @GetMapping("")
    public String viewPersonalInfo(Model model) throws IllegalAccessException {
        viewedWorker = loggedUser;

        workersFields = workerService.getWorkersFields(viewedWorker);

        model.addAttribute("data", new WorkerDataContainer(workersFields));

        return String.format("%s/personal-page", templateDir);
    }


    @GetMapping("/{id}")
    public String viewPersonalInfo(@PathVariable
                                   int id,
                                   Model model)
                                   throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, WorkerNotFoundException {

        viewedWorker = workerService.getWorkerExtObject(workerService.getWorker(id));
        workersFields = workerService.getWorkersFields(viewedWorker);

        model.addAttribute("data", new WorkerDataContainer(workersFields));

        return String.format("%s/personal-page", templateDir);
    }


    //DELETE PERSONAL PAGE
    @DeleteMapping("/deletion")
    public String deletePersonalInfo(@ModelAttribute
                                     WorkerDataContainer data,
                                     Model model) {

        workerService.deleteWorker(viewedWorker);

        data.setWorkersData(workersFields);
        data.setOperationSuccessful(true);
        model.addAttribute("data", data);

        return String.format("%s/personal-page", templateDir);
    }


    //UPDATE PERSONAL PAGE
    @GetMapping("/edition")
    public String updatePersonalInfo(@ModelAttribute
                                     WorkerDataContainer data,
                                     Model model) {

        tempWorker = null;

        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));
        data.setWorkersData(workersFields);

        model.addAttribute("data", data);

        return String.format("%s/edit-personal-page", templateDir);
    }


    @PutMapping("/edition")
    public String processUpdate(@ModelAttribute
                                WorkerDataContainer data,
                                Model model)
                                throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        List<String> errorMessages;

        tempWorker = (T) viewedWorker.getClass().getDeclaredConstructor().newInstance();
        tempWorker.setId(viewedWorker.getId());
        tempWorker.setWorkerType(viewedWorker.getWorkerType());

        bindingService.bindToWorkerEntity(data.getWorkersData(), tempWorker);
        errorMessages = bindingService.getErrorMessages();

        if (errorMessages.isEmpty()) {
            workerService.save(tempWorker);
            data.setOperationSuccessful(true);
        } else data.setErrorMessages(errorMessages);

        model.addAttribute("data", data);

        return String.format("%s/edit-personal-page", templateDir);
    }


    //CREATE PERSONAL PAGE
    @GetMapping("/creation")
    public String createPersonalInfo(Model model) throws IllegalAccessException {

        tempWorker = null;

        workersFields = workerService.getWorkersFields(new Worker());

        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));

        model.addAttribute("data", new WorkerDataContainer(workersFields));
        model.addAttribute("workerTypes", hierarchyService.getSubordinateWorkerTypesList());

        return String.format("%s/create-personal-page", templateDir);
    }


    @PostMapping("/creation")
    public String processCreate(@ModelAttribute
                                WorkerDataContainer data,
                                //Check if the user picked a type of worker to add
                                @RequestParam(name = "pickType", required = false) String isRequestingType,
                                @RequestParam("selectedOption") String selectedOption,
                                Model model)
                                throws Exception {

        //If the user wants to choose a specific worker type
        if (isRequestingType != null) {
            if (!selectedOption.equals("-- Select --")) {
                WorkerType type = hierarchyService.getSubordinateWorkerTypes().get(selectedOption);

                tempWorker = workerService.createWorker(type);

                workersFields = workerService.getWorkersFields(tempWorker);
                workersFields.removeIf(workerData -> "id".equals(workerData.getField()));
                workerService.mergeWorkersFieldsData(workersFields, data.getWorkersData());

                data.setWorkersData(workersFields);
            } else data.setErrorMessage("No worker type selected");
        //If the user wants to add a new worker
        } else {
            List<String> errorMessages;

            if (tempWorker != null) {
                bindingService.bindToWorkerEntity(data.getWorkersData(), tempWorker);
                errorMessages = bindingService.getErrorMessages();

                if (errorMessages.isEmpty()) {
                    workerService.save(tempWorker);
                    data.setOperationSuccessful(true);
                } else data.setErrorMessages(errorMessages);
            } else data.setErrorMessage("No worker type selected");
        }

        model.addAttribute("workerTypes", hierarchyService.getSubordinateWorkerTypesList());
        model.addAttribute("data", data);

        return String.format("%s/create-personal-page", templateDir);
    }
}
