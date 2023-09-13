package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.worker.Manager;
import com.company.CompanyApp.entity.worker.Worker;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Lazy
@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final Manager manager;


    //CONSTRUCTORS
    public ManagerController(Worker loggedUser) {
        manager = (Manager) loggedUser;
    }


    public void register() {

    }
}
