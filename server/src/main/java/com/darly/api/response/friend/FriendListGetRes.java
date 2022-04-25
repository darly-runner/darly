package com.darly.api.response.friend;

import com.darly.api.response.account.AccountLoginPostRes;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.friend.FriendTitleMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FriendListGetRes extends BaseResponseBody {
    private List<FriendTitleMapping> users;

    public static FriendListGetRes of(Integer statusCode, String message, List<FriendTitleMapping> friendList) {
        return FriendListGetRes.builder()
                .statusCode(statusCode)
                .message(message)
                .friendList(friendList)
                .build();
    }

    @Builder
    public FriendListGetRes(Integer statusCode, String message, List<FriendTitleMapping> friendList) {
        super(statusCode, message);
        this.users = friendList;
    }
}
