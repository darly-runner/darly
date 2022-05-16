package com.darly.db.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserNowMapping {

    private Long userId;
    private String userNickname;
    private String userImage;
    private Float userNowDistance;
    private Integer userNowPace;
}
