package com.hsesslingen.jodel.mappers;

import com.hsesslingen.jodel.DTOs.JodelDTO;
import com.hsesslingen.jodel.entities.Jodel;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JodelMapper {
    public JodelDTO toJodelDTO(Jodel jodel) {
        JodelDTO jodelDto = new JodelDTO();
        jodelDto.setId(jodel.getId());
        jodelDto.setParentJodelId(jodel.getParentJodel() != null ? jodel.getParentJodel().getId() : null);
        jodelDto.setBarbarian(jodel.getBarbarian());
        if (jodel.getChildJodels() != null) {
            jodelDto.setChildJodels(jodel.getChildJodels().stream().map(Jodel::getId).toList());
        } else {
            jodelDto.setChildJodels(Collections.emptyList());
        }
        jodelDto.setPostId(jodel.getPostId());
        jodelDto.setContent(jodel.getContent());
        jodelDto.setUpvotes(jodel.getUpVoteCount());
        jodelDto.setDownvotes(jodel.getDownVoteCount());
        return jodelDto;
    }
}
