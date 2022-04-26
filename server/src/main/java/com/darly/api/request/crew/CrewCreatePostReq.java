package com.darly.api.request.crew;

import lombok.Getter;

@Getter
public class CrewCreatePostReq {
    private String crewName;
    private String crewDesc;
    private Long crewAddress;
    private String crewJoin;
}
