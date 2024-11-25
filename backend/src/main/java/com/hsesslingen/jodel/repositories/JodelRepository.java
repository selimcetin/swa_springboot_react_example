package com.hsesslingen.jodel.repositories;

import com.hsesslingen.jodel.entities.Jodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JodelRepository extends JpaRepository<Jodel, Long> {
}