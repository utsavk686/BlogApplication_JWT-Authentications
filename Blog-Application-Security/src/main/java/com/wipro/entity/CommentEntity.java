package com.wipro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "comments")
public class CommentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment cannot be empty")
    @Size(min = 3, max = 200, message = "Comment must be between 3 and 200 characters")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)  
    private BlogEntity blog;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BlogEntity getBlog() {
        return blog;
    }

    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }
}
