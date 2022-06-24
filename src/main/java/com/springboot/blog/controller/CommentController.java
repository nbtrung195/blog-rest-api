package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD REST APIs for Comment Resources")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "Create Comment REST API")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Comment By Post ID REST API")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

    @ApiOperation(value = "Get a Comment By Comment ID REST API")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> gotCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @ApiOperation(value = "Update a Comment By Comment ID REST API")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @ApiOperation(value = "Delete a Comment By Comment ID REST API")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
