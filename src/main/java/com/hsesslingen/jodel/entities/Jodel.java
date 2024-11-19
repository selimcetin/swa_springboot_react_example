package com.hsesslingen.jodel.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
public class Jodel {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "parent_jodel_id")
    private Jodel parentJodel;

    @OneToMany(mappedBy = "parentJodel")
    private List<Jodel> childJodels;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "jodel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JodelBarbarian> votes;

    @JsonProperty("upvotes")
    public int getUpVoteCount() {
        return Collections.frequency(votes, VoteType.UP);
    }

    @JsonProperty("downvotes")
    public int getDownVoteCount() {
        return Collections.frequency(votes, VoteType.DOWN);
    }
}
