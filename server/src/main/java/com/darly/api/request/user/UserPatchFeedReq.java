package com.darly.api.request.user;

import com.darly.db.entity.event.Event;
import com.darly.db.entity.userFeed.UserFeed;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel("UserPatchFeedRequest")
@Builder
public class UserPatchFeedReq {
    @ApiModelProperty(name="userFeedImage", example="string")
    String userFeedImage;

    public static UserFeed ofPatch(UserFeed userFeed, String userFeedImage) {
        return UserFeed.builder()
                .userFeedId(userFeed.getUserFeedId())
                .userId(userFeed.getUserId())
                .userFeedImage(userFeedImage)
                .build();
    }
}
