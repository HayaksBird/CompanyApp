package com.company.CompanyApp.app.dao;

import com.company.CompanyApp.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}