package com.hsesslingen.jodel.services;

import com.hsesslingen.jodel.entities.Jodel;
import com.hsesslingen.jodel.entities.JodelBarbarian;
import com.hsesslingen.jodel.entities.PostBarbarian;
import com.hsesslingen.jodel.entities.VoteType;
import com.hsesslingen.jodel.exceptions.EntityIdNotFoundException;
import com.hsesslingen.jodel.repositories.JodelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class JodelService {
    @Autowired
    private JodelRepository jodelRepository;

    public Jodel getJodelById(long id){
        return jodelRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException(id, "Post"));
    }
}
