package com.hsesslingen.jodel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    @JsonProperty("post_id")
    public Long getPostId() {
        return post != null ? post.getId() : null;
    }

    @OneToMany(mappedBy = "jodel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JodelBarbarian> votes;

    private String content;

    @JsonProperty("upvotes")
    public int getUpVoteCount() {
        return votes.stream()
                .mapToInt(vote -> vote.getVoteType() == VoteType.UP ? 1 : 0)
                .sum();
    }

    @JsonProperty("downvotes")
    public int getDownVoteCount() {
        return votes.stream()
                .mapToInt(vote -> vote.getVoteType() == VoteType.DOWN ? 1 : 0)
                .sum();
    }
}