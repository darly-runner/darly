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
    private Integer totalPages; //전체 페이지 수
    private Long totalCrew; //전체 크루 수
    private Integer currentPage; //현재 페이지 번호
    private Integer numberOfCrew; //현재 페이지의 크루 수
    private List<CrewTitleMapping> crew;

    @Builder
    public CrewSearchGetRes(Integer statusCode, String message, Page<CrewTitleMapping> page, Integer currentPage) {
        super(statusCode, message);
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalCrew = page.getTotalElements();
        this.currentPage = currentPage;
        this.numberOfCrew = page.getNumberOfElements();
        this.crew = page.getContent();
    }
}
