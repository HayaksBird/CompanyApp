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

/**
 * This bean is lazy, because it must be initialized after the SecurityContextHolder is set.
 */
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
    /**
     * View logged user's personal page.
     */
    @GetMapping("")
    public String viewPersonalInfo(Model model) throws IllegalAccessException {
        viewedWorker = loggedUser;

        workersFields = workerService.getWorkersFields(viewedWorker);

        model.addAttribute("data", new WorkerDataContainer(workersFields));

        return String.format("%s/personal-page", templateDir);
    }


    /**
     * View a personal page of a worker.
     */
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
    /**
     * Delete the viewed worker from the system.
     */
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
    /**
     * Prepare the worker update template for the user.
     */
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


    /**
     * Process the worker update template.
     * Create an object model, which will be bound with the data from the form.
     * Note that the id and the worker type are always predefined, since they could not
     * be altered.
     * If no errors to be found during the binding, then we persist the worker.
     */
    @PutMapping("/edition")
    public String processUpdate(@ModelAttribute
                                WorkerDataContainer data,
                                Model model)
                                throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        tempWorker = (T) viewedWorker.getClass().getDeclaredConstructor().newInstance();
        tempWorker.setId(viewedWorker.getId());
        tempWorker.setWorkerType(viewedWorker.getWorkerType());

        saveWorker(data);

        model.addAttribute("data", data);

        return String.format("%s/edit-personal-page", templateDir);
    }


    //CREATE PERSONAL PAGE
    /**
     * Prepare the worker creation template for the user.
     */
    @GetMapping("/creation")
    public String createPersonalInfo(Model model) throws IllegalAccessException {

        tempWorker = null;

        workersFields = workerService.getWorkersFields(new Worker());

        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));

        model.addAttribute("data", new WorkerDataContainer(workersFields));
        model.addAttribute("workerTypes", hierarchyService.getSubordinateWorkerTypesList());

        return String.format("%s/create-personal-page", templateDir);
    }


    /**
     * Process the worker creation template.
     * If the user requests a certain worker type, then we provide him with one.
     * If the user wants to submit the new worker, then we first check if he previously
     * requested a certain worker type (which is necessary). If not, we throw him an error message.
     * If he has, then we validate his input data. If no errors to be found, then we persist the worker.
     */
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
                requestExtObject(selectedOption, data);
            } else data.setErrorMessage("No worker type selected");
        //If the user wants to add a new worker
        } else {
            if (tempWorker != null) {
                saveWorker(data);
            } else data.setErrorMessage("No worker type selected");
        }

        model.addAttribute("workerTypes", hierarchyService.getSubordinateWorkerTypesList());
        model.addAttribute("data", data);

        return String.format("%s/create-personal-page", templateDir);
    }


    /**
     * This method is summoned only during the worker creation, when
     * the user requests a certain type of worker.
     * We get the list of fields for the new worker type, and if the user already
     * entered some data before requesting the worker type, then we rewrite the data form
     * the old list to the new one (so that the user doesn't need to manually rewrite).
     */
    private void requestExtObject(String selectedOption, WorkerDataContainer data) throws Exception {
        WorkerType type = hierarchyService.getSubordinateWorkerTypes().get(selectedOption);

        tempWorker = workerService.createWorker(type);

        workersFields = workerService.getWorkersFields(tempWorker);
        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));
        workerService.mergeWorkersFieldsData(workersFields, data.getWorkersData());

        data.setWorkersData(workersFields);
    }


    /**
     * Attempt to save a worker in to the database (update/create).
     * If the operation is successful, then the container's status message
     * is set accordingly. However, if failed, the error list is provided to the
     * container.
     */
    private void saveWorker(WorkerDataContainer data) throws IllegalAccessException {
        List<String> errorMessages = bindingService.bindToWorkerEntity(data.getWorkersData(), tempWorker);

        if (errorMessages.isEmpty()) {
            workerService.save(tempWorker);
            data.setOperationSuccessful(true);
        } else data.setErrorMessages(errorMessages);
    }
}