package com.darly.api.request.match;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MatchPatchReq {
    private String matchTitle;
    private Short matchMaxPerson;
    private Float matchGoalDistance;
}
