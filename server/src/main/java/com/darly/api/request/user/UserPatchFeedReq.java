package com.darly.api.request.user;

import com.darly.db.entity.event.Event;
import com.darly.db.entity.userFeed.UserFeed;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@Getter
@ApiModel("UserPatchFeedRequest")
@Builder
public class UserPatchFeedReq {
    @Nullable
    @ApiModelProperty(name="userFeedImage", example="string")
    private MultipartFile userFeedImage;

    public static UserFeed ofPatch(UserFeed userFeed, String userFeedImage) {
        return UserFeed.builder()
                .userFeedId(userFeed.getUserFeedId())
                .userId(userFeed.getUserId())
                .userFeedImage(userFeedImage)
                .build();
    }
}
