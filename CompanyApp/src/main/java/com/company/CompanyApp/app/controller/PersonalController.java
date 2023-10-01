package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.validation.dto.ModelData;
import com.company.CompanyApp.validation.dto.ModelDataContainer;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.validation.service.BindingService;
import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import com.company.CompanyApp.validation.service.ModelDataService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 *
 * This controller handles everything to do with the user's/worker's personal page:
 * view, update, delete, create.
 */
@Lazy
@Controller
@RequestMapping("/personal")
public class PersonalController <T extends Worker> {
    private final BindingService bindingService;
    private final IHierarchyService hierarchyService;
    private final IWorkerService workerService;
    private final ModelDataService modelDataService;
    private final String templateDir;
    private final T loggedUser;
    private T viewedWorker;
    private T tempWorker;
    private List<ModelData> workersFields;


    //CONSTRUCTORS
    public PersonalController(T loggedUser,
                              IWorkerService workerService,
                              BindingService bindingService,
                              IHierarchyService hierarchyService,
                              ModelDataService modelDataService) {

        templateDir = "app/personal";
        this.loggedUser = loggedUser;

        this.modelDataService = modelDataService;
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

        workersFields = modelDataService.getModelsFields(viewedWorker);

        model.addAttribute("data", new ModelDataContainer(workersFields));

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

        workersFields = modelDataService.getModelsFields(viewedWorker);

        model.addAttribute("data", new ModelDataContainer(workersFields));

        return String.format("%s/personal-page", templateDir);
    }


    //DELETE PERSONAL PAGE
    /**
     * Delete the viewed worker from the system.
     */
    @DeleteMapping("/deletion")
    public String deletePersonalInfo(@ModelAttribute
                                     ModelDataContainer data,
                                     Model model) {

        workerService.deleteWorker(viewedWorker);

        data.setModelData(workersFields);
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
                                     ModelDataContainer data,
                                     Model model) {

        tempWorker = null;

        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));
        data.setModelData(workersFields);

        model.addAttribute("data", data);

        return String.format("%s/edit-personal-page", templateDir);
    }


    /**
     * Process the worker update template.
     * Create an object model, which will be bound with the data from the form.
     * Note that the static data is automatically set.
     * If no errors to be found during the binding, then we persist the worker.
     */
    @PutMapping("/edition")
    public String processUpdate(@ModelAttribute
                                ModelDataContainer data,
                                Model model)
                                throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        tempWorker = (T) viewedWorker.getClass().getDeclaredConstructor().newInstance();
        //Set static data
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

        workersFields = modelDataService.getModelsFields(new Worker());

        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));

        model.addAttribute("data", new ModelDataContainer(workersFields));
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
                                ModelDataContainer data,
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
    private void requestExtObject(String selectedOption, ModelDataContainer data) throws Exception {
        WorkerType type = hierarchyService.getSubordinateWorkerTypes().get(selectedOption);

        tempWorker = workerService.createWorker(type);

        workersFields = modelDataService.getModelsFields(tempWorker);
        workersFields.removeIf(workerData -> "id".equals(workerData.getField()));
        modelDataService.mergeModelDataList(workersFields, data.getModelData());

        data.setModelData(workersFields);
    }


    /**
     * Attempt to save a worker in to the database (update/create).
     * If the operation is successful, then the container's status message
     * is set accordingly. However, if failed, the error list is provided to the
     * container.
     */
    private void saveWorker(ModelDataContainer data) throws IllegalAccessException {
        List<String> errorMessages = bindingService.bindToModelEntity(data.getModelData(), tempWorker);

        if (errorMessages.isEmpty()) {
            workerService.save(tempWorker);
            data.setOperationSuccessful(true);
        } else data.setErrorMessages(errorMessages);
    }
}