package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.Worker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 *
 * Acts like a dispatcher controller
 */
@Lazy
@Controller
@RequestMapping("/home")
public class  HomeController <T extends Worker> {
    private final T loggedUser;


    public HomeController(T loggedUser) {
        this.loggedUser = loggedUser;
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", loggedUser);
        return "app/home-page";
    }
}