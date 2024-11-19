package com.hsesslingen.jodel.entities;

import com.hsesslingen.jodel.repositories.JodelBarbarianRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(JodelBarbarianId.class)
@Getter
@Setter
public class JodelBarbarian {
    @Id
    @ManyToOne
    @JoinColumn(name = "jodel_id")
    private Jodel jodel;

    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private Barbarian barbarian;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
