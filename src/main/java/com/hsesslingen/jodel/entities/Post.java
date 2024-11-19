package com.hsesslingen.jodel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @Column(columnDefinition = "geography(POINT, 4326)")
    private Point location;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Barbarian barbarian;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostBarbarian> votes;

    @JsonProperty("upvotes") // Include this in JSON response
    public int getUpVoteCount() {
        return Collections.frequency(votes, VoteType.UP);
    }

    @JsonProperty("downvotes") // Include this in JSON response
    public int getDownVoteCount() {
        return Collections.frequency(votes, VoteType.DOWN);
    }
}
