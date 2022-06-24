package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(value = "CRUD Rest Apis for Post resources")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "Create Post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new  ResponseEntity(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Posts REST API")
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION  , required = false) String sortDir){
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @ApiOperation(value = "Get Post By ID REST API")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @ApiOperation(value = "Update Post By ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Post By ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Deleted Post Successfully", HttpStatus.OK);
    }

}
