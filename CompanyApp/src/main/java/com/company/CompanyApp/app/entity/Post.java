package com.company.CompanyApp.app.entity;

import com.company.CompanyApp.validation.annotations.NoNullEntity;
import com.company.CompanyApp.validation.annotations.ViewName;
import jakarta.persistence.*;

import java.time.LocalDate;

@NoNullEntity
@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id")
    private int id;

    @ViewName(message = "Title")
    @Column(name = "title")
    private String title;

    @ViewName(message = "Content")
    @Column(name = "content")
    private String content;

    @ViewName(message = "Author")
    @Column(name = "author")
    private String author;


    @Column(name = "post_date")
    private LocalDate date;


    @Column(name = "worker_id")
    private int workerId;


    //CONSTRUCTORS
    public Post() {}


    //Setters & Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
}