package com.hsesslingen.jodel.services;

import com.hsesslingen.jodel.entities.Barbarian;
import com.hsesslingen.jodel.exceptions.EntityIdNotFoundException;
import com.hsesslingen.jodel.repositories.BarbarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarbarianService {
    @Autowired
    private BarbarianRepository barbarianRepository;

    public Barbarian getBarbarianById(String username){
        return barbarianRepository.findById(username).orElseThrow(() -> new EntityIdNotFoundException(username, "Barbarian"));
    }

    public Barbarian getOrCreateBarbarian(String username) {
        return barbarianRepository.findById(username)
                .orElseGet(() -> {
                    Barbarian newBarbarian = new Barbarian();
                    newBarbarian.setUsername(username);
                    return barbarianRepository.save(newBarbarian);
                });
    }
}
