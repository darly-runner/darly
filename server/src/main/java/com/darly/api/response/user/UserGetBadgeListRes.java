package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.Badge;
import com.darly.db.entity.UserBadge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
