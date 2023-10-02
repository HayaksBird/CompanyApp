package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.entity.Post;
import com.company.CompanyApp.exception.ItemNotFoundException;

import java.util.List;

public interface IPostService {
    //CRUD
    void save(Post post);

    void delete(int id);

    Post getPost(int id) throws ItemNotFoundException;

    List<Post> getAllPosts();
}
