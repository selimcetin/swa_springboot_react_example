package com.hsesslingen.jodel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsesslingen.jodel.serializers.PointDeserializer;
import com.hsesslingen.jodel.serializers.PointSerializer;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
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
    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    private Point location;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private Barbarian barbarian;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jodel> jodels = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostBarbarian> votes = new ArrayList<>();

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
