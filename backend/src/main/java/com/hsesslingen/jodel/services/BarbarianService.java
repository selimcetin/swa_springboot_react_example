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

    public Barbarian getBarbarianById(String username) {
    return barbarianRepository.findById(username)
        .orElseThrow(() -> new EntityIdNotFoundException(username, "Barbarian"));
    }

    public Barbarian getDefaultBarbarian() {
        // Fetch a default Barbarian by username or create a new one
        return barbarianRepository.findById("default_user")
                .orElseGet(() -> {
                    Barbarian defaultBarbarian = new Barbarian();
                    defaultBarbarian.setUsername("default_user");
                    return barbarianRepository.save(defaultBarbarian);
                });
    }
}
