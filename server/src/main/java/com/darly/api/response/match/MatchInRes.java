package com.darly.api.response.match;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.user.UserMatchMapping;
import com.darly.db.repository.record.RecordRepository;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("MatchInResponse")
public class MatchInRes extends BaseResponseBody {

    private String matchTitle;
    private Float matchGoalDistance;
    private Short matchCurPerson;
    private Short matchMaxPerson;
    private List<UserMatchMapping> users;

    @Builder
    public MatchInRes(Integer statusCode, String message, List<UserMatchMapping> userMatches, Match match){
        super(statusCode, message);
        this.matchTitle = match.getMatchTitle();
        this.matchGoalDistance = match.getMatchGoalDistance();
        this.matchCurPerson = match.getMatchCurPerson();
        this.matchMaxPerson = match.getMatchMaxPerson();
        this.users = userMatches;
    }
}
