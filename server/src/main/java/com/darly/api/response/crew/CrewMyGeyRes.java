package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.CrewMyMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CrewMyGeyRes extends BaseResponseBody {
    private List<CrewMyMapping> crew;

    @Builder
    public CrewMyGeyRes(Integer statusCode, String message, List<CrewMyMapping> crew) {
        super(statusCode, message);
        this.crew = crew;
    }
}
