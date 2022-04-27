package com.darly.db.entity.crew;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrewMyMapping {
    private Long crewId;
    private String crewName;
    private String crewImage;

    @QueryProjection
    public CrewMyMapping(Long crewId, String crewName, String crewImage) {
        this.crewId = crewId;
        this.crewName = crewName;
        this.crewImage = crewImage;
    }
}
