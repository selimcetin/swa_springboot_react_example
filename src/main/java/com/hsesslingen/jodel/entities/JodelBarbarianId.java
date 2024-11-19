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
public class JodelBarbarianId implements Serializable {
    private Jodel jodel;
    private Barbarian barbarian;

    public JodelBarbarianId(Jodel jodel, Barbarian barbarian){
        this.jodel = jodel;
        this.barbarian = barbarian;
    }
}
