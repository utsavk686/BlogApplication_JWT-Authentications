package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.BlogDto;
import com.wipro.dto.CommentDto;
import com.wipro.service.BlogService;
import com.wipro.service.CommentService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/blogs")
@Validated
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private CommentService commentService;

	// Create a Blog
	@PostMapping
	public ResponseEntity<BlogDto> createBlog(@Valid @RequestBody BlogDto blogDto) {
		BlogDto createdBlog = blogService.createBlog(blogDto);
		return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
	}

	// Get Blog by ID
	@GetMapping("/{id}")
	public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
		BlogDto blogDto = blogService.getBlogById(id);
		return new ResponseEntity<>(blogDto, HttpStatus.OK);
	}

	// Update Blog by ID
	@PutMapping("/update/{id}")
	public ResponseEntity<BlogDto> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogDto blogDto) {
		BlogDto updatedBlog = blogService.updateBlog(id, blogDto);
		return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
	}

	// Delete Blog by ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteBlog(@PathVariable Long id) {
		boolean isDeleted = blogService.deleteBlog(id);
		return new ResponseEntity<>(isDeleted, HttpStatus.OK);
	}
	// Get all Blogs
		@GetMapping("/getall")
		public ResponseEntity<List<BlogDto>> getAllBlogs() {
		    List<BlogDto> blogs = blogService.getAllBlogs();
		    return new ResponseEntity<>(blogs, HttpStatus.OK);
		}

	// Post a Comment on a Blog
	@PostMapping("/comment")
	public ResponseEntity<CommentDto> postComment(@Valid @RequestBody CommentDto commentDto) {
		CommentDto postedComment = commentService.postComment(commentDto);
		return new ResponseEntity<>(postedComment, HttpStatus.OK);
	}
	
	
	

	@GetMapping("/getallcomment")
	public ResponseEntity<List<CommentDto>> getAllComments() {
	    List<CommentDto> comments = commentService.getAllComments();
	    return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	
	@GetMapping("/comment/{blogId}")
	public ResponseEntity<List<CommentDto>> getCommentById(@PathVariable Long blogId) {
	    List<CommentDto> commentDto = commentService.getCommentByBlogId(blogId);
	    return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}



	
}