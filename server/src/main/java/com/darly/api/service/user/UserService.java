package com.darly.api.service.user;

import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchFeedReq;
import com.darly.api.request.user.UserPatchReq;
import com.darly.api.request.user.UserPostFeedReq;
import com.darly.db.entity.badge.Badge;
import com.darly.db.entity.user.User;
import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.userFeed.UserFeed;

import java.util.List;

public interface UserService {
    User getUserByUserId(Long userId);
    User patchUser(UserPatchReq userPatchReq, Long userId);
    User patchUserCondition(UserPatchConditionReq userPatchConditionReq, Long userId);
    List<Badge> getBadgeList(Long userId);
    List<FriendTitleMapping> getUserSearchList(Long userId, String nickname);
    UserFeed postUserFeed(UserPostFeedReq userPostFeedReq, Long userId);
    void deleteUserFeed(Long userFeedId);
    UserFeed patchUserFeed(UserPatchFeedReq userPatchFeedReq, Long userFeedId);
}
