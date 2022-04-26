package com.darly.api.service.user;

import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchFeedReq;
import com.darly.api.request.user.UserPatchReq;
import com.darly.api.request.user.UserPostFeedReq;
import com.darly.db.entity.address.Address;
import com.darly.db.entity.badge.Badge;
import com.darly.db.entity.user.User;
import com.darly.db.entity.user.UserBadge;
import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.userFeed.UserFeed;
import com.darly.db.repository.user.UserBadgeRepository;
import com.darly.db.repository.user.UserRepository;
import com.darly.db.repository.user.UserRepositorySupport;
import com.darly.db.repository.userFeed.UserFeedRepository;
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

    @Autowired
    private UserFeedRepository userFeedRepository;

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User patchUser(UserPatchReq userPatchReq, Long userId) {
        User user = userRepository.findById(userId).get();
        User patchUser = UserPatchReq.ofPatch(user, userPatchReq.getUserNickname(), userPatchReq.getUserImage(), userPatchReq.getUserMessage());

        return userRepository.save(patchUser);
    }

    @Override
    public User patchUserCondition(UserPatchConditionReq userPatchConditionReq, Long userId) {
        User user = userRepository.findById(userId).get();
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

    @Override
    public List<FriendTitleMapping> getUserSearchList(Long userId, String nickname) {
        return userRepositorySupport.findUserTitleSearchList(userId, nickname);
    }

    @Override
    public UserFeed postUserFeed(UserPostFeedReq userPostFeedReq, Long userId) {
        UserFeed userFeed = UserFeed.builder()
                .userId(userId)
                .userFeedImage(userPostFeedReq.getUserFeedImage())
                .build();

        return userFeedRepository.save(userFeed);
    }

    @Override
    public void deleteUserFeed(Long userFeedId) {
        UserFeed userFeed = userFeedRepository.findById(userFeedId).get();

        userFeedRepository.delete(userFeed);
    }

    @Override
    public UserFeed patchUserFeed(UserPatchFeedReq userPatchFeedReq, Long userFeedId) {
        UserFeed userFeed = userFeedRepository.findById(userFeedId).get();

        UserFeed patchUserFeed = UserPatchFeedReq.ofPatch(userFeed, userPatchFeedReq.getUserFeedImage());

        return userFeedRepository.save(patchUserFeed);
    }
}
