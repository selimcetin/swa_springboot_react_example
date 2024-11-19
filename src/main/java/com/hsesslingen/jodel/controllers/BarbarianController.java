package com.hsesslingen.jodel.controllers;

import com.hsesslingen.jodel.entities.Barbarian;
import com.hsesslingen.jodel.repositories.BarbarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barbarians")
public class BarbarianController {
    @Autowired
    private BarbarianRepository barbarianRepository;

    @GetMapping
    public ResponseEntity<List<Barbarian>> getBarbarians(){
        return ResponseEntity.ok(barbarianRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<Barbarian> createBarbarian(@RequestBody Barbarian barbarian) {
        Barbarian savedBarbarian = barbarianRepository.save(barbarian);
        return ResponseEntity.ok(savedBarbarian);
    }
}
