package com.hsesslingen.jodel.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private long id;
    private String content;
    private Point location;
    private String barbarianUsername;
    private int upvotes;
    private int downvotes;
    private List<Long> jodelIdList;
}
