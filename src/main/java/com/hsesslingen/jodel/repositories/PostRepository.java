package com.hsesslingen.jodel.repositories;

import com.hsesslingen.jodel.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
                SELECT p
                FROM Post p
                WHERE ST_DWithin(
                    p.location,
                    ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326),
                    :radius
                ) = true
            """, nativeQuery = true)
    List<Post> findPostsWithinRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius);
}