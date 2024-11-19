package com.hsesslingen.jodel.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class PostBarbarianId implements Serializable {
    private Post post;
    private Barbarian barbarian;

    public PostBarbarianId(Post post, Barbarian barbarian){
        this.post = post;
        this.barbarian = barbarian;
    }
}
