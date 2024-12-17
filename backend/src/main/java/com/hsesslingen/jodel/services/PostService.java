package com.hsesslingen.jodel.services;

import com.hsesslingen.jodel.DTOs.PostDTO;
import com.hsesslingen.jodel.entities.Post;
import com.hsesslingen.jodel.entities.PostBarbarian;
import com.hsesslingen.jodel.entities.VoteType;
import com.hsesslingen.jodel.exceptions.EntityIdNotFoundException;
import com.hsesslingen.jodel.mappers.PostMapper;
import com.hsesslingen.jodel.repositories.BarbarianRepository;
import com.hsesslingen.jodel.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BarbarianRepository barbarianRepository;
    @Autowired
    private PostMapper postMapper;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    public Post getPostById(long id){
        return postRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException(id, "Post"));
    }

    public List<Post> getPostsWithinRadius(double longitude, double latitude, double radius) {
        return postRepository.findPostsWithinRadius(longitude, latitude, radius);
    }

    public PostDTO getPostDto(Post post) {
        return postMapper.toPostDTO(post);
    }

    public List<PostDTO> getPostDtoList(List<Post> postList) {
        return postList.stream().map(postMapper::toPostDTO).toList();
    }
}
