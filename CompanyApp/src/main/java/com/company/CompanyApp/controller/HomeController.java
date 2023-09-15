package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 */
@Lazy
@Controller
@RequestMapping("/home")
public class HomeController {
    private final WorkerType type;
    private final Worker loggedUser;


    public HomeController(Worker loggedUser) {
        this.loggedUser = loggedUser;
        type = loggedUser.getWorkerType();
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", loggedUser);
        return "app/home-page";
    }


    @GetMapping("/department")
    public void viewDepartmentInfo(HttpServletResponse response) throws IOException {
        response.sendRedirect(String.format("/%s/department", type.name().toLowerCase()));
    }


    @GetMapping("/personal")
    public void viewPersonalInfo(HttpServletResponse response) throws IOException {
        response.sendRedirect(String.format("/%s/personal", type.name().toLowerCase()));
    }
}
