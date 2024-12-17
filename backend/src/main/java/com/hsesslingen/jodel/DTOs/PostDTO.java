package com.hsesslingen.jodel.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsesslingen.jodel.entities.Barbarian;
import com.hsesslingen.jodel.serializers.PointSerializer;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private long id;
    private String content;

    @JsonSerialize(using = PointSerializer.class)
    private Point location;
    private Barbarian barbarian;
    private int upvotes;
    private int downvotes;
    private List<Long> jodelIdList;
}
