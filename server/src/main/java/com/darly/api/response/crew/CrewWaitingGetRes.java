package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.user.UserTitleMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("CrewWaitingGetResponse")
public class CrewWaitingGetRes extends BaseResponseBody {
    private List<UserTitleMapping> users;

    @Builder
    public CrewWaitingGetRes(Integer statusCode, String message, List<UserTitleMapping> users) {
        super(statusCode, message);
        this.users = users;
    }
}
