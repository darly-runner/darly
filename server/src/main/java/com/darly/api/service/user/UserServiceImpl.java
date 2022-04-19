package com.darly.api.service.user;

import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchReq;
import com.darly.api.response.user.UserGetBadgeListRes;
import com.darly.db.entity.Badge;
import com.darly.db.entity.User;
import com.darly.db.entity.UserBadge;
import com.darly.db.repository.user.UserBadgeRepository;
import com.darly.db.repository.user.UserRepository;
import com.darly.db.repository.user.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User patchUser(UserPatchReq userPatchReq, Long userId) {
        User user = userRepository.findById(userId).get();

        user.setUserNickname(userPatchReq.getUserNickname());
        user.setUserImage(userPatchReq.getUserImage());
        userRepository.save(user);

        return user;
    }

    @Override
    public User patchUserCondition(UserPatchConditionReq userPatchConditionReq, Long userId) {
        User user = userRepository.findById(userId).get();

        user.setUserGoalDistance(userPatchConditionReq.getUserGoalDistance());
        user.setUserGoalTime(userPatchConditionReq.getUserGoalTime());
        userRepository.save(user);

        return user;
    }

    @Override
    public List<Badge> getBadgeList(Long userId) {
        List<UserBadge> badges = userBadgeRepository.findUserBadgesByUser_UserId(userId);

        List<Badge> badgeList = new ArrayList<>();
        for(int i = 0; i < badges.size(); i++) {
            Badge badge = new Badge();
            badge.setBadgeId(badges.get(i).getBadge().getBadgeId());
            badge.setBadgeName(badges.get(i).getBadge().getBadgeName());
            badge.setBadgeImage(badges.get(i).getBadge().getBadgeImage());
            badge.setBadgeCondition(badges.get(i).getBadge().getBadgeCondition());
            badgeList.add(badge);
        }

        return badgeList;
    }
}
