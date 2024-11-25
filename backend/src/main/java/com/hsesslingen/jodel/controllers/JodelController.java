package com.hsesslingen.jodel.controllers;

import com.hsesslingen.jodel.DTOs.JodelDTO;
import com.hsesslingen.jodel.entities.*;
import com.hsesslingen.jodel.mappers.JodelMapper;
import com.hsesslingen.jodel.repositories.JodelRepository;
import com.hsesslingen.jodel.exceptions.EntityIdNotFoundException;
import com.hsesslingen.jodel.services.BarbarianService;
import com.hsesslingen.jodel.services.JodelService;
import com.hsesslingen.jodel.services.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jodels")
public class JodelController {
    @Autowired
    private BarbarianService barbarianService;
    @Autowired
    private JodelService jodelService;
    @Autowired
    private PostService postService;


    @GetMapping
    public ResponseEntity<List<JodelDTO>> getAllJodels() {
        List<Jodel> jodelList = jodelService.getAll();
        List<JodelDTO> jodelDtoList = jodelService.getJodelDtoList(jodelList);
        return ResponseEntity.ok(jodelDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JodelDTO> getJodelWithId(@PathVariable Long id) {
        Jodel jodel = jodelService.getJodelById(id);
        return ResponseEntity.ok(jodelService.getJodelDto(jodel));
    }

    @PostMapping("/post")
    public ResponseEntity<JodelDTO> createJodelOnPost(@RequestBody Jodel jodel, @RequestParam long postId) {
        Post targetPost = postService.getPostById(postId);

        jodel.setPost(targetPost);
        Jodel savedJodel = jodelService.saveJodel(jodel);

        return ResponseEntity.ok(jodelService.getJodelDto(savedJodel));
    }

    @PostMapping("/jodel")
    public ResponseEntity<JodelDTO> createJodelOnJodel(@RequestBody Jodel jodel, @RequestParam long jodelId) {
        Jodel targetJodel = jodelService.getJodelById(jodelId);

        jodel.setParentJodel(targetJodel);
        Jodel savedJodel = jodelService.saveJodel(jodel);

        return ResponseEntity.ok(jodelService.getJodelDto(savedJodel));
    }

    @PostMapping("/vote")
    @Transactional
    public ResponseEntity<Void> voteJodel(@RequestParam long jodelId, @RequestParam String username, @RequestParam int voteType) {
        Jodel targetJodel = jodelService.getJodelById(jodelId);
        Barbarian targetBarbarian = barbarianService.getBarbarianById(username);
        JodelBarbarian jodelBarbarian = new JodelBarbarian();

        jodelBarbarian.setJodel(targetJodel);
        jodelBarbarian.setBarbarian(targetBarbarian);
        jodelBarbarian.setVoteType(VoteType.getValue(voteType));

        targetJodel.getVotes().add(jodelBarbarian);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
