package com.darly.api.response.match;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.user.UserMatchMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("MatchInResponse")
public class MatchInRes extends BaseResponseBody {

    private String matchTitle;
    private Float matchGoalDistance;
    private Short matchCurPerson;
    private Short matchMaxPerson;
    private String hostNickname;
    private Long myUserId;
    private Integer imHost;
    private Character matchStatus;
    private List<UserMatchMapping> users;

    @Builder
    public MatchInRes(Integer statusCode, String message, Integer imHost, Character matchStatus, Long myUserId, List<UserMatchMapping> userMatches, Match match){
        super(statusCode, message);
        this.matchTitle = match.getMatchTitle();
        this.myUserId = myUserId;
        this.imHost = imHost;
        this.matchStatus = matchStatus;
        this.hostNickname = match.getHost().getUserNickname();
        this.matchGoalDistance = match.getMatchGoalDistance();
        this.matchCurPerson = match.getMatchCurPerson();
        this.matchMaxPerson = match.getMatchMaxPerson();
        this.users = userMatches;
    }
}
