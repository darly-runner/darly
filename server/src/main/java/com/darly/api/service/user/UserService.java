package com.darly.api.service.user;

import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchReq;
import com.darly.db.entity.Badge;
import com.darly.db.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUserId(Long userId);
    User patchUser(UserPatchReq userPatchReq, Long userId);
    User patchUserCondition(UserPatchConditionReq userPatchConditionReq, Long userId);
    List<Badge> getBadgeList(Long userId);
}
