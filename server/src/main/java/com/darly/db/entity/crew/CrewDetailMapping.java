package com.darly.db.entity.crew;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CrewDetailMapping {
    private String crewName;
    private String crewDesc;
    private String crewNotice;
    private String crewHost;
    private String crewLocation;
    private String crewImage;
    private Integer crewPeople;
    private Integer crewFeedNum;

    @QueryProjection
    public CrewDetailMapping(String crewName, String crewDesc, String crewNotice, String crewHost, String crewLocation, String crewImage, Integer crewPeople, Integer crewFeedNum) {
        this.crewName = crewName;
        this.crewDesc = crewDesc;
        this.crewNotice = crewNotice;
        this.crewHost = crewHost;
        this.crewLocation = crewLocation;
        this.crewImage = crewImage;
//        this.crewPeople = crewPeople;
//        this.crewFeedNum = crewFeedNum;
    }
}
