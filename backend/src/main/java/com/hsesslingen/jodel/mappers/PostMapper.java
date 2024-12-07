package com.hsesslingen.jodel.mappers;
import java.util.Collections; // Add this import

import com.hsesslingen.jodel.DTOs.PostDTO;
import com.hsesslingen.jodel.entities.Jodel;
import com.hsesslingen.jodel.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDTO toPostDTO(Post post) {
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setLocation(post.getLocation());
        
        // Null check for barbarian
        if (post.getBarbarian() != null) {
            postDto.setBarbarianUsername(post.getBarbarian().getUsername());
        } else {
            postDto.setBarbarianUsername("Unknown"); // Default value or placeholder
        }

        postDto.setUpvotes(post.getUpVoteCount());
        postDto.setDownvotes(post.getDownVoteCount());
        
        // Null check for jodels list
        if (post.getJodels() != null) {
            postDto.setJodelIdList(post.getJodels().stream()
                .map(Jodel::getId)
                .toList());
        } else {
            postDto.setJodelIdList(Collections.emptyList()); // Set to empty list if null
        }

        return postDto;
    }
}
