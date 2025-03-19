package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.CommentDto;
import com.wipro.entity.CommentEntity;
import com.wipro.exceptions.CommentNotFoundException;
import com.wipro.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogService blogService;  
    @Autowired
    private ModelMapper modelMapper;  

    @Override
    public CommentDto postComment(CommentDto commentDto) {
        
        blogService.getBlogById(commentDto.getBlogId());
        CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);        
        commentEntity = commentRepository.save(commentEntity);
        return modelMapper.map(commentEntity, CommentDto.class);
    }
    
    @Override
    public List<CommentDto> getAllComments() {
        List<CommentEntity> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
    
    public List<CommentDto> getCommentByBlogId(Long blogId) {
    	blogService.getBlogById(blogId);
    	List<CommentEntity> comments = commentRepository.findByBlogId(blogId);
    	return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }
  


}
