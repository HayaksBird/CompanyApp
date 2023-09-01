package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.Worker;
import com.company.CompanyApp.service.IWorkerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;

@Lazy
@Controller
@RequestMapping("/home")
public class HomeController {
    private final Worker loggedUser;
    private final IWorkerService workerService;


    public HomeController(IWorkerService workerService) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        this.workerService = workerService;

        loggedUser = workerService.getWorker(Integer.parseInt(id));
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", loggedUser);
        return "home-page";
    }
}
