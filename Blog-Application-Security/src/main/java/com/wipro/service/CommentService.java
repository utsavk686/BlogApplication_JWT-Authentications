package com.wipro.service;

import java.util.List;

import com.wipro.dto.CommentDto;

public interface CommentService {

	public CommentDto postComment(CommentDto commentDto);
	
	public List<CommentDto> getAllComments();
	public List<CommentDto> getCommentByBlogId(Long blogId);

}