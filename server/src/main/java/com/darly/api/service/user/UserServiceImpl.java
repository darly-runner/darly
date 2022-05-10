package com.darly.api.service.user;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchFeedReq;
import com.darly.api.request.user.UserPatchReq;
import com.darly.api.request.user.UserPostFeedReq;
import com.darly.api.service.file.FileProcessService;
import com.darly.db.entity.badge.Badge;
import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.user.User;
import com.darly.db.entity.user.UserBadge;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    @Autowired
    private UserFeedRepository userFeedRepository;

    @Autowired
    private FileProcessService fileProcessService;

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User patchUser(UserPatchReq userPatchReq, Long userId) {
        User user = userRepository.findById(userId).get();
        String url = null;

        if (userPatchReq.getUserImage() != null) {
            if (user.getUserImage() != null)
                fileProcessService.deleteImage(user.getUserImage());
            url = fileProcessService.uploadImage(userPatchReq.getUserImage(), "user");
        }

        if (userPatchReq.getUserNickname().length() > 0)
            user.setUserNickname(userPatchReq.getUserNickname());
        if (userPatchReq.getUserImage() != null)
            user.setUserImage(url);
        user.setUserMessage(userPatchReq.getUserMessage());
        return userRepository.save(user);
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
        for (int i = 0; i < badges.size(); i++) {
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
                .userFeedImage(fileProcessService.uploadImage(userPostFeedReq.getUserFeedImage(), "user"))
                .build();
        return userFeedRepository.save(userFeed);
    }

    @Override
    public void deleteUserFeed(Long userFeedId) {
        UserFeed userFeed = userFeedRepository.findById(userFeedId).get();

        if(userFeed.getUserFeedImage() != null) {
            fileProcessService.deleteImage(userFeed.getUserFeedImage());
        }
        userFeedRepository.delete(userFeed);
    }

    @Override
    public UserFeed patchUserFeed(UserPatchFeedReq userPatchFeedReq, Long userFeedId) {
        UserFeed userFeed = userFeedRepository.findById(userFeedId).get();
        String url = null;

        // 기존의 이미지 삭제
        if(userFeed.getUserFeedImage() != null) {
            fileProcessService.deleteImage(userFeed.getUserFeedImage());
        }

        if(userPatchFeedReq.getUserFeedImage() != null) {
            url = fileProcessService.uploadImage(userPatchFeedReq.getUserFeedImage(),"feed");
        }

        UserFeed patchUserFeed = UserPatchFeedReq.ofPatch(userFeed, url);

        return userFeedRepository.save(patchUserFeed);
    }

    @Override
    public void updateUserRecord(Long userId, RecordCreatePostReq recordCreatePostReq) {
        User user = getUserByUserId(userId);
        user.setUserTotalDistance(user.getUserTotalDistance() + recordCreatePostReq.getRecordDistance());
        user.setUserTotalTime(user.getUserTotalTime() + recordCreatePostReq.getRecordTime());
        if (user.getUserTotalHeart() == null)
            user.setUserTotalHeart(recordCreatePostReq.getRecordHeart());
        if (user.getUserHeartNum() == null) {
            if (recordCreatePostReq.getRecordHeart() != 0)
                user.setUserHeartNum(1);
        } else {
            if (recordCreatePostReq.getRecordHeart() != 0)
                user.setUserHeartNum(user.getUserHeartNum() + 1);
        }
        user.setUserTotalCalories(user.getUserTotalCalories() + recordCreatePostReq.getRecordCalories());
        user.setUserTotalPace(user.getUserTotalPace() + recordCreatePostReq.getRecordPace());
        userRepository.save(user);
    }

    @Override
    public boolean existUserNickname(String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }
}
