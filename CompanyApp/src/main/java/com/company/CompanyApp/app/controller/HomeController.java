package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 *
 * Acts like a dispatcher controller, sending requests to different
 * endpoints depending on the user's choice.
 */
@Lazy
@Controller
@RequestMapping("/home")
public class  HomeController <T extends Worker> {
    private final T loggedUser;
    private final IHierarchyService hierarchyManagementService;


    public HomeController(T loggedUser,
                          IHierarchyService hierarchyManagementService) {

        this.loggedUser = loggedUser;
        this.hierarchyManagementService = hierarchyManagementService;
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", loggedUser);
        model.addAttribute("roles", hierarchyManagementService.getLoggedUsersRoles());
        return "app/home-page";
    }
}