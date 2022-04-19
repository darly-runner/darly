package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.Badge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("UserGetBadgeListResponse")
public class UserGetBadgeListRes extends BaseResponseBody {
    @ApiModelProperty(name="badgeList", example="badge1, badge2,...")
    private List<Badge> badgeList = new ArrayList<>();

    public static UserGetBadgeListRes of(List<Badge> badgeList, Integer statusCode, String message) {
        UserGetBadgeListRes res = new UserGetBadgeListRes();

        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setBadgeList(badgeList);

        return res;
    }
}
