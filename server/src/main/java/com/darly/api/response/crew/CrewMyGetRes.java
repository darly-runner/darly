package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.CrewMyMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("CrewMyGetResponse")
public class CrewMyGetRes extends BaseResponseBody {
    private List<CrewMyMapping> crew;

    @Builder
    public CrewMyGetRes(Integer statusCode, String message, List<CrewMyMapping> crew) {
        super(statusCode, message);
        this.crew = crew;
    }
}
