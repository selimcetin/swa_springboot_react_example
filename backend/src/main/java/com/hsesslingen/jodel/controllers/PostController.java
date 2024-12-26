package com.hsesslingen.jodel.controllers;

import com.hsesslingen.jodel.DTOs.PostDTO;
import com.hsesslingen.jodel.entities.Barbarian;
import com.hsesslingen.jodel.entities.Post;
import com.hsesslingen.jodel.entities.PostBarbarian;
import com.hsesslingen.jodel.entities.VoteType;
import com.hsesslingen.jodel.mappers.PostMapper;
import com.hsesslingen.jodel.repositories.BarbarianRepository;
import com.hsesslingen.jodel.repositories.PostRepository;
import com.hsesslingen.jodel.services.BarbarianService;
import com.hsesslingen.jodel.services.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private BarbarianService barbarianService;
    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> postList = postService.getAll();
        List<PostDTO> postDtoList = postService.getPostDtoList(postList);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/radius")
    public ResponseEntity<List<PostDTO>> getAllPostsWithinRadius(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
        List<Post> postsWithinRadius = postService.getPostsWithinRadius(longitude, latitude, radius);
        List<PostDTO> postDtoWithinRadius = postService.getPostDtoList(postsWithinRadius);
        return ResponseEntity.ok(postDtoWithinRadius);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostWithId(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        PostDTO postDto = postService.getPostDto(post);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody Post post) {
        Post savedPost = postService.savePost(post);
        PostDTO postDTO = postMapper.toPostDTO(savedPost);

        return ResponseEntity.ok(postDTO);
    }

    @PostMapping("/vote")
    @Transactional
    public ResponseEntity<Void> votePost(@RequestParam long postId, @RequestParam String username, @RequestParam int voteType) {
        Post targetPost = postService.getPostById(postId);
        Barbarian targetBarbarian = barbarianService.getBarbarianById(username);
        PostBarbarian postBarbarian = new PostBarbarian();

        postBarbarian.setPost(targetPost);
        postBarbarian.setBarbarian(targetBarbarian);
        postBarbarian.setVoteType(VoteType.getValue(voteType));

        targetPost.getVotes().add(postBarbarian);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
