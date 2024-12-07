package com.hsesslingen.jodel.controllers;

import com.hsesslingen.jodel.DTOs.PostDTO;
import com.hsesslingen.jodel.entities.Barbarian;
import com.hsesslingen.jodel.entities.Post;
import com.hsesslingen.jodel.entities.PostBarbarian;
import com.hsesslingen.jodel.entities.VoteType;
import com.hsesslingen.jodel.repositories.BarbarianRepository;
import com.hsesslingen.jodel.repositories.PostRepository;
import com.hsesslingen.jodel.services.BarbarianService;
import com.hsesslingen.jodel.services.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private BarbarianService barbarianService;
    @Autowired
    private PostService postService;

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
public ResponseEntity<Post> createPost(@RequestBody Post post) {
    System.out.println("Received Post: " + post.getBarbarian().getUsername());

    if (post.getBarbarian() == null) {
        Barbarian defaultBarbarian = barbarianService.getDefaultBarbarian();
        post.setBarbarian(defaultBarbarian);
    }

    Post savedPost = postService.savePost(post);
    return ResponseEntity.ok(savedPost);
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
