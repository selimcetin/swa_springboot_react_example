package com.hsesslingen.jodel.services;

import com.hsesslingen.jodel.DTOs.JodelDTO;
import com.hsesslingen.jodel.entities.Jodel;
import com.hsesslingen.jodel.entities.JodelBarbarian;
import com.hsesslingen.jodel.entities.PostBarbarian;
import com.hsesslingen.jodel.entities.VoteType;
import com.hsesslingen.jodel.exceptions.EntityIdNotFoundException;
import com.hsesslingen.jodel.mappers.JodelMapper;
import com.hsesslingen.jodel.repositories.JodelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class JodelService {
    @Autowired
    private JodelRepository jodelRepository;

    @Autowired
    private JodelMapper jodelMapper;

    public List<Jodel> getAll() {
        return jodelRepository.findAll();
    }

    public Jodel getJodelById(long id){
        return jodelRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException(id, "Jodel"));
    }

    public Jodel saveJodel(Jodel jodel) {
        return jodelRepository.save(jodel);
    }

    public JodelDTO getJodelDto(Jodel jodel) {
        return jodelMapper.toJodelDTO(jodel);
    }

    public List<JodelDTO> getJodelDtoList(List<Jodel> jodelList) {
        return jodelList.stream().map(jodelMapper::toJodelDTO).toList();
    }


}
