package com.darly.api.response.match;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.MatchTitleMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
    public MatchListGetRes(Integer statusCode, String message, Page<MatchTitleMapping> page, Integer currentPage) {
        super(statusCode, message);
        this.size = page.getSize();
        this.totalMatches = page.getTotalElements();
        this.currentPage = currentPage;
        this.numberOfMatch = page.getNumberOfElements();
        this.matches = page.getContent();
    }
}
