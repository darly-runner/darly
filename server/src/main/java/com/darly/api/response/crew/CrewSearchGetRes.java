package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.CrewTitleMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class CrewSearchGetRes extends BaseResponseBody {
    private Integer size; //한 페이지의 크루 수
    private Long totalCrew; //전체 크루 수
    private Integer currentPage; //현재 페이지 번호
    private Integer numberOfCrew; //현재 페이지의 크루 수
    private List<CrewTitleMapping> crew;

    @Builder
    public CrewSearchGetRes(Integer statusCode, String message, List<CrewTitleMapping> crewList, Integer currentPage, Long crewCount, Integer size) {
        super(statusCode, message);
        this.size = size;
        this.totalCrew = crewCount;
        this.currentPage = currentPage;
        this.numberOfCrew = crewList.size();
        this.crew = crewList;
    }
}
