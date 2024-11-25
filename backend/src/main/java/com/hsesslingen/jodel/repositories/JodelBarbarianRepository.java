package com.hsesslingen.jodel.repositories;

import com.hsesslingen.jodel.entities.JodelBarbarian;
import com.hsesslingen.jodel.entities.JodelBarbarianId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JodelBarbarianRepository extends JpaRepository<JodelBarbarian, JodelBarbarianId> {
}
