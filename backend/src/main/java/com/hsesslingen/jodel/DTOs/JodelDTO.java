package com.hsesslingen.jodel.DTOs;
import com.hsesslingen.jodel.entities.Barbarian;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JodelDTO {
    private long id;
    private Long parentJodelId;
    private Long postId;
    private Barbarian barbarian;
    private List<Long> childJodels;
    private String content;
    private int upvotes;
    private int downvotes;
}
