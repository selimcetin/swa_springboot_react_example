package com.hsesslingen.jodel.controllers;

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

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private BarbarianService barbarianService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BarbarianRepository barbarianRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {

        List<Post> list = postRepository.findAll();
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/radius")
    public ResponseEntity<List<Post>> getAllPostsWithinRadius(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
        return ResponseEntity.ok(postService.getPostsWithinRadius(longitude, latitude, radius));
    }

    @GetMapping("/{id}")
    public Post getPostWithId(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postRepository.save(post);
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
