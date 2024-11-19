package com.hsesslingen.jodel.repositories;

import com.hsesslingen.jodel.entities.Barbarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbarianRepository extends JpaRepository<Barbarian, String> {
}
