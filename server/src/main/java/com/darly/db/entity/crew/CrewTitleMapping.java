package com.darly.db.entity.crew;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrewTitleMapping {
    private Long crewId;
    private String crewName;
    private String crewDesc;
    private String crewImage;
    private String crewAddress;
    private Integer crewPeopleNum;

    @QueryProjection
    public CrewTitleMapping(Long crewId, String crewName, String crewDesc, String crewImage, String crewAddress, Integer crewPeopleNum) {
        this.crewId = crewId;
        this.crewName = crewName;
        this.crewDesc = crewDesc;
        this.crewImage = crewImage;
        this.crewAddress = crewAddress;
        this.crewPeopleNum = crewPeopleNum;
    }
}
