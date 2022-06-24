package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment comment1 = commentRepository.save(comment);
        CommentDto commentDto1 = mapper.map(comment1, CommentDto.class);
        return commentDto1;
    }

    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments){
            CommentDto commentDto = mapper.map(comment, CommentDto.class);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment comment1 = commentRepository.save(comment);
        CommentDto commentDto1 = mapper.map(comment1, CommentDto.class);
        return commentDto1;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }
}
