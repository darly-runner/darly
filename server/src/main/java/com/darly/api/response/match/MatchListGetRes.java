package com.darly.api.response.match;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchTitleMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("MatchListGetResponse")
public class MatchListGetRes extends BaseResponseBody {
    private Integer size;
    private Long totalMatches;
    private Integer currentPage;
    private Integer numberOfMatch;
    private List<MatchTitleMapping> matches;

    @Builder
    public MatchListGetRes(Integer statusCode, String message, Page<Match> page, Integer currentPage) {
        super(statusCode, message);
        this.size = page.getSize();
        this.totalMatches = page.getTotalElements();
        this.currentPage = currentPage;
        this.numberOfMatch = page.getNumberOfElements();
        matches = new ArrayList<>();
        List<Match> matchList = page.getContent();
        for (Match match : matchList) {
            this.matches.add(MatchTitleMapping.builder()
                    .hostNickname(match.getHost().getUserNickname())
                    .hostImage(match.getHost().getUserImage())
                    .matchId(match.getMatchId())
                    .matchTitle(match.getMatchTitle())
                    .matchMaxPerson(match.getMatchMaxPerson())
                    .matchCurPerson(match.getMatchCurPerson())
                    .matchGoalDistance(match.getMatchGoalDistance())
                    .matchDate(match.getMatchDate())
                    .matchStartTime(match.getMatchStartTime())
                    .matchStatus(match.getMatchStatus())
                    .build());
        }
    }
}
