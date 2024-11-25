package com.hsesslingen.jodel.repositories;

import com.hsesslingen.jodel.entities.PostBarbarian;
import com.hsesslingen.jodel.entities.PostBarbarianId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostBarbarianRepository extends JpaRepository<PostBarbarian, PostBarbarianId> {
}
