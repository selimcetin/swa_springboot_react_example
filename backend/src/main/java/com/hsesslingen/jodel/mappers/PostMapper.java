package com.hsesslingen.jodel.mappers;

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
        postDto.setBarbarian(post.getBarbarian());
        postDto.setUpvotes(post.getUpVoteCount());
        postDto.setDownvotes(post.getDownVoteCount());
        postDto.setJodelIdList(post.getJodels().stream()
                .map(Jodel::getId)
                .toList());

        return postDto;
    }
}
