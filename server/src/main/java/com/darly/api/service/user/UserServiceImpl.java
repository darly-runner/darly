package com.darly.api.service.user;

import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchReq;
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
        User patchUser = UserPatchReq.ofPatch(user, userPatchReq.getUserNickname(), userPatchReq.getUserImage());

        return userRepository.save(patchUser);
    }

    @Override
    public User patchUserCondition(UserPatchConditionReq userPatchConditionReq, Long userId) {
        User user = userRepository.findById(userId).get();
        System.out.println(userPatchConditionReq.getUserGoalDistance());
        System.out.println(userPatchConditionReq.getUserGoalTime());
        User patchUserCondition = UserPatchConditionReq.ofPatchCondition(user, userPatchConditionReq.getUserGoalDistance(), userPatchConditionReq.getUserGoalTime());

        return userRepository.save(patchUserCondition);
    }

    @Override
    public List<Badge> getBadgeList(Long userId) {
        List<UserBadge> badges = userBadgeRepository.findUserBadgesByUser_UserId(userId);

        List<Badge> badgeList = new ArrayList<>();
        for(int i = 0; i < badges.size(); i++) {
            Badge badge = Badge.builder()
                            .badgeId((badges.get(i).getBadge().getBadgeId()))
                            .badgeName(badges.get(i).getBadge().getBadgeName())
                            .badgeImage(badges.get(i).getBadge().getBadgeImage())
                            .badgeCondition(badges.get(i).getBadge().getBadgeCondition())
                            .build();
            badgeList.add(badge);
        }

        return badgeList;
    }
}
