package com.company.CompanyApp.app.service.implementation;

import com.company.CompanyApp.app.dao.PostRepository;
import com.company.CompanyApp.app.entity.Post;
import com.company.CompanyApp.app.service.IPostService;
import com.company.CompanyApp.exception.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService {
    private final PostRepository postRepository;


    //CONSTRUCTORS
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public void save(Post post) {
        postRepository.save(post);
    }


    @Override
    public void delete(int id) {
        postRepository.deleteById(id);
    }


    @Override
    public Post getPost(int id) throws ItemNotFoundException {
        var post = postRepository.findById(id);

        if (post.isPresent()) return post.get();
        else throw new ItemNotFoundException(Post.class, id);
    }


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
