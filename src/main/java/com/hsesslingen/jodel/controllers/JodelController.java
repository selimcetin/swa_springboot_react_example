package com.hsesslingen.jodel.controllers;

import com.hsesslingen.jodel.entities.*;
import com.hsesslingen.jodel.repositories.JodelRepository;
import com.hsesslingen.jodel.exceptions.EntityIdNotFoundException;
import com.hsesslingen.jodel.services.BarbarianService;
import com.hsesslingen.jodel.services.JodelService;
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
    private JodelRepository jodelRepository;
    @Autowired
    private BarbarianService barbarianService;
    @Autowired
    private JodelService jodelService;

    @GetMapping
    public ResponseEntity<List<Jodel>> getAllPosts() {
        return ResponseEntity.ok(jodelRepository.findAll());
    }

    @GetMapping("/{id}")
    public Jodel getPostWithId(@PathVariable Long id) {
        return jodelRepository.findById(id).orElseThrow( () -> new EntityIdNotFoundException(id, "Comment"));
    }

    @PostMapping
    public ResponseEntity<Jodel> createPost(@RequestBody Jodel jodel) {
        Jodel savedJodel = jodelRepository.save(jodel);
        return ResponseEntity.ok(savedJodel);
    }

    @PostMapping("/{jodelId}{username}{voteType}")
    @Transactional
    public ResponseEntity<Void> votePost(@RequestParam long jodelId, @RequestParam String username, @RequestParam int voteType) {
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
