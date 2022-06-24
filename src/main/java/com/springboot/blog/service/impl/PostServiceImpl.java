package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final ModelMapper mapper;
    private final PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        Post post1 = postRepository.save(post);
        PostDto postDto1 = mapper.map(post1, PostDto.class);
        return postDto1;
    }
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Post> posts = postRepository.findAll(PageRequest.of(pageNo, pageSize, sort));
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts){
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtos.add(postDto);
        }
        PostResponse postResponse = PostResponse
                .builder()
                .content(postDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(postDtos.size())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
        return postResponse;
    }
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        postRepository.save(post);
        PostDto postDto1 = mapper.map(post, PostDto.class);
        return postDto1;
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
