package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.BlogDto;
import com.wipro.dto.CommentDto;
import com.wipro.entity.BlogEntity;
import com.wipro.exceptions.BlogNotFoundException;
import com.wipro.repository.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ModelMapper modelMapper; 

    @Override
    public BlogDto createBlog(BlogDto blogDto) {
		
    	BlogEntity blogEntity = modelMapper.map(blogDto, BlogEntity.class);
    	blogEntity = blogRepository.save(blogEntity);
    	return modelMapper.map(blogEntity, BlogDto.class);
    
    }

    @Override
    public BlogDto getBlogById(Long id) {        
        // Fetch the BlogEntity with comments
        BlogEntity blogEntity = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + id));
       BlogDto blogDto = modelMapper.map(blogEntity, BlogDto.class);

   
        List<CommentDto> commentDtos = blogEntity.getComments().stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

       
        blogDto.setComments(commentDtos);
        return blogDto;
    }

    @Override
    public BlogDto updateBlog(Long id, BlogDto blogDto) {
        BlogEntity existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + id));
        
        modelMapper.map(blogDto, existingBlog);
        existingBlog.setId(id);
        return modelMapper.map(existingBlog, BlogDto.class);
    }

    @Override
    public Boolean deleteBlog(Long id) {
        
        if(!blogRepository.existsById(id)) {
        	throw new BlogNotFoundException("Blog not found with ID: " + id);
        }
        
        blogRepository.deleteById(id);
        return true;
    }

    
    @Override
    public List<BlogDto> getAllBlogs() {
        List<BlogEntity> blogs = blogRepository.findAll();
        
        return blogs.stream()
                .map(blog -> modelMapper.map(blog, BlogDto.class))
                .collect(Collectors.toList());
    }

	
}