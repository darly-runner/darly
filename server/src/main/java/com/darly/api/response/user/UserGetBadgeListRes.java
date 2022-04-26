package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.badge.Badge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel("UserGetBadgeListResponse")
public class UserGetBadgeListRes extends BaseResponseBody {
    @ApiModelProperty(name="badgeList", example="badge1, badge2,...")
    private List<Badge> badgeList = new ArrayList<>();

    public static UserGetBadgeListRes of(List<Badge> badgeList, Integer statusCode, String message) {

        return UserGetBadgeListRes.builder()
                .statusCode(statusCode)
                .message(message)
                .badgeList(badgeList)
                .build();
    }

    @Builder
    public UserGetBadgeListRes(Integer statusCode, String message, List<Badge> badgeList) {
        super(statusCode, message);
        this.badgeList = badgeList;
    }
}
