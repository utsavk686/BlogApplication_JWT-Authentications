package com.wipro.service;

import java.util.List;

import com.wipro.dto.BlogDto;

public interface BlogService {

	public BlogDto createBlog(BlogDto blogDto);

	public BlogDto getBlogById(Long id);

	public BlogDto updateBlog(Long id, BlogDto blogDto);

	public Boolean deleteBlog(Long id);
	
	public List<BlogDto> getAllBlogs();

}
