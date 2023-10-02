package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.Post;
import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.service.IPostService;
import com.company.CompanyApp.exception.ItemNotFoundException;
import com.company.CompanyApp.validation.dto.ModelData;
import com.company.CompanyApp.validation.dto.ModelDataContainer;
import com.company.CompanyApp.validation.service.BindingService;
import com.company.CompanyApp.validation.service.ModelDataService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Lazy
@Controller
@RequestMapping("/post")
public class PostController <T extends Worker> {
    private final T loggedUser;
    private final ModelDataService modelDataService;
    private final BindingService bindingService;
    private final IPostService postService;
    private final String templateDir;
    private List<ModelData> postsFields;
    private Post tempPost;
    private Post viewedPost;


    //CONSTRUCTORS
    public PostController(T loggedUser,
                          IPostService postService,
                          ModelDataService modelDataService,
                          BindingService bindingService) {

        templateDir = "app/post";
        this.loggedUser = loggedUser;

        this.postService = postService;
        this.modelDataService = modelDataService;
        this.bindingService = bindingService;
    }


    //CREATE POST
    /**
     *
     */
    @GetMapping("/creation")
    public String createPost(Model model) throws IllegalAccessException {
        tempPost = null;

        postsFields = modelDataService.getModelsFields(new Post());

        model.addAttribute("data", new ModelDataContainer(postsFields));

        return String.format("%s/create-post-page", templateDir);
    }


    /**
     *
     */
    @PostMapping("/creation")
    public String processCreation(@ModelAttribute
                                  ModelDataContainer data,
                                  Model model) throws IllegalAccessException {

        tempPost = new Post();
        //Set static data
        tempPost.setWorkerId(loggedUser.getId());
        tempPost.setDate(LocalDate.now());

        savePost(data);

        model.addAttribute("data", data);

        return String.format("%s/create-post-page", templateDir);
    }


    //UPDATE POST
    /**
     *
     */
    @GetMapping("/edition/{id}")
    public String updatePost(@PathVariable
                             int id,
                             Model model) throws ItemNotFoundException, IllegalAccessException {

        tempPost = null;

        viewedPost = postService.getPost(id);
        viewedPost.setContent(transformLineBreaks(false, viewedPost.getContent()));

        postsFields = modelDataService.getModelsFields(viewedPost);

        model.addAttribute("data", new ModelDataContainer(postsFields));

        return String.format("%s/edit-post-page", templateDir);
    }


    /**
     *
     */
    @PutMapping("/edition")
    public String processUpdate(@ModelAttribute
                                ModelDataContainer data,
                                Model model) throws IllegalAccessException {

        tempPost = new Post();
        //Set static data
        tempPost.setWorkerId(loggedUser.getId());
        tempPost.setDate(LocalDate.now());
        tempPost.setId(viewedPost.getId());

        savePost(data);

        model.addAttribute("data", data);

        return String.format("%s/edit-post-page", templateDir);
    }


    //DELETE POST
    /**
     *
     */
    @DeleteMapping("/deletion/{id}")
    public String deletePost(@PathVariable
                             int id,
                             HttpServletResponse response) throws IOException {

        postService.delete(id);

        response.sendRedirect("/home");

        return null;
    }


    /**
     *
     */
    private void savePost(ModelDataContainer data) throws IllegalAccessException {
        List<String> errorMessages = bindingService.bindToModelEntity(data.getModelData(), tempPost);

        if (errorMessages.isEmpty()) {
            tempPost.setContent(transformLineBreaks(true, tempPost.getContent()));
            postService.save(tempPost);
            data.setOperationSuccessful(true);
        } else data.setErrorMessages(errorMessages);
    }


    /**
     * If line separators are present then substitute them with <br> for html
     * and vice versa if an update is requested.
     */
    private String transformLineBreaks(boolean toHtmlStyle, String content) {
        if (toHtmlStyle) return content.replace(System.lineSeparator(), "<br>");
        else return content.replace("<br>", System.lineSeparator());
    }
}
