package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.service.IPostService;
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
    private final IPostService postService;


    //CONSTRUCTORS
    public HomeController(T loggedUser,
                          IHierarchyService hierarchyManagementService,
                          IPostService postService) {

        this.loggedUser = loggedUser;
        this.hierarchyManagementService = hierarchyManagementService;
        this.postService = postService;
    }


    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("user", loggedUser);
        model.addAttribute("roles", hierarchyManagementService.getLoggedUsersRoles());

        return "app/home-page";
    }
}