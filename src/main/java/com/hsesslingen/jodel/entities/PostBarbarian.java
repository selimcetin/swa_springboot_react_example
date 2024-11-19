package com.hsesslingen.jodel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(PostBarbarianId.class)
@Getter
@Setter
public class PostBarbarian {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private Barbarian barbarian;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}