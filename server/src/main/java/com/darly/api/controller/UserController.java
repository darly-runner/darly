package com.darly.api.controller;


import com.darly.api.request.user.*;
import com.darly.api.response.user.*;
import com.darly.api.service.event.UserEventService;
import com.darly.api.service.friend.FriendService;
import com.darly.api.service.user.UserService;
import com.darly.api.service.userAddress.UserAddressService;
import com.darly.api.service.userFeed.UserFeedService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.address.AddressNameMapping;
import com.darly.db.entity.badge.Badge;
import com.darly.db.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 유저에 대한 명령 컨트롤러
 * /users
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserFeedService userFeedService;

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    FriendService friendService;

    @Autowired
    UserEventService userEventService;

    // 1. 유저 정보 조회 GET
    @GetMapping
    @ApiOperation(value = "유저정보조회", notes = "userNickname, userEmail, userPoint, userImage를 가져옴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<UserGetRes> getUser(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        User user = userService.getUserByUserId(userId);

        return ResponseEntity.ok(UserGetRes.of(user, 200, "success"));
    }

    // 2. 유저정보수정 PATCH
    @PatchMapping
    @ApiOperation(value = "유저정보수정", notes = "userNickname, userImage, userMessage 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<BaseResponseBody> patchUser(@ModelAttribute UserPatchReq userPatchReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userService.patchUser(userPatchReq, userId);
        userAddressService.putUserAddressByStringList(userPatchReq.getUserAddresses(), userId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "success"));
    }

    // 3. 총누적정보조회 GET /stats
    @GetMapping("/stats")
    @ApiOperation(value = "유저총누적정보조회", notes = "userTotalDistance, userToTalTime, " +
            "userToTalHeart, userHeartNum, userMinPace를 가져옴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<UserStatsGetRes> getUserStats(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        User user = userService.getUserByUserId(userId);

        return ResponseEntity.ok(UserStatsGetRes.of(user, 200, "success"));
    }

    // 4. 뱃지목록조회 GET /badge
    @GetMapping("/badge")
    @ApiOperation(value = "유저뱃지조회", notes = "현재 유저가 보유한 badge의 badgeName, badgeImage, badgeCondition을 가져옴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<UserGetBadgeListRes> getBadge(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        List<Badge> badgeList = userService.getBadgeList(userId);

        return ResponseEntity.ok(UserGetBadgeListRes.of(badgeList, 200, "success"));
    }

    // 5. 거리/시간수정 PATCH /condition
    @PatchMapping("/condition")
    @ApiOperation(value = "목표거리,시간수정", notes = "userGoalDistance, userGoalTime 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<BaseResponseBody> patchUserCondition(UserPatchConditionReq userPatchConditionReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userService.patchUserCondition(userPatchConditionReq, userId);

        return ResponseEntity.ok(BaseResponseBody.of(200, "success"));
    }

    // 6. 지역정보조회 GET /address
    @GetMapping("/address")
    @ApiOperation(value = "지역정보조회", notes = "유저의 지역정보를 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<UserGetAddress> getUserAddress(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        List<AddressNameMapping> addresses = userAddressService.getAddressNameList(userId);

        return ResponseEntity.ok(UserGetAddress.of(addresses, 200, "success"));
    }

    // 7. 지역정보수정 PUT /address
    @PutMapping("/address")
    @ApiOperation(value = "지역정보수정", notes = "유저의 지역정보를 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<BaseResponseBody> putUserAddress(UserAddressPutReq userAddressPutReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userAddressService.putUserAddress(userAddressPutReq, userId);

        return ResponseEntity.ok(BaseResponseBody.of(200, "success"));
    }

    // 8. 유저피드작성 POST /feed
    @PostMapping("/feed")
    @ApiOperation(value = "유저피드작성", notes = "feedImage 작성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<BaseResponseBody> postUserFeed(@ModelAttribute UserPostFeedReq userPostFeedReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userService.postUserFeed(userPostFeedReq, userId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "message"));
    }

    // 9. 유저피드목록 GET /feed?page
    @GetMapping("/feed")
    @ApiOperation(value = "유저피드가져오기", notes = "user의 feedImage들 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<UserFeedGetRes> getUserFeed(Authentication authentication, @PageableDefault(size = 15, sort = "user_feed_id", direction = Sort.Direction.DESC) Pageable page) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());

        return ResponseEntity.ok(UserFeedGetRes.builder()
                .page(userFeedService.getUserFeedList(userId, page))
                .currentPage(page.getPageNumber())
                .statusCode(200)
                .message("Success")
                .build());
    }

    // 10. 유저피드삭제 DELETE /feed/{feedId}
    @DeleteMapping("/feed/{userFeedId}")
    @ApiOperation(value = "유저피드삭제", notes = "userFeed 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<BaseResponseBody> deleteUserFeed(@PathVariable("userFeedId") Long userFeedId) {
        userService.deleteUserFeed(userFeedId);

        return ResponseEntity.ok(BaseResponseBody.of(200, "message"));
    }

    // 11. 유저피드수정 PATCH /feed/{feedId}
    @PatchMapping("/feed/{userFeedId}")
    @ApiOperation(value = "유저피드수정", notes = "userFeedImage 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<BaseResponseBody> patchUserFeed(UserPatchFeedReq userPatchFeedReq, @PathVariable("userFeedId") Long userFeedId) {
        userService.patchUserFeed(userPatchFeedReq, userFeedId);

        return ResponseEntity.ok(BaseResponseBody.of(200, "message"));
    }

    // U-012
    @GetMapping("/profile")
    @ApiOperation(value = "유저 프로필 조회", notes = "유저 프로필 조회하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getUserProfile(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(UserGetProfileRes.builder()
                .statusCode(200)
                .message("Success get user profile")
                .user(userService.getUserByUserId(userId))
                .addressList(userAddressService.getAddressList(userId))
                .userFriendNum(friendService.getFriendNum(userId))
                .build());
    }


    // U-013
    @PostMapping("/nickname")
    @ApiOperation(value = "닉네임 중복 검사", notes = "닉네임 중복 검사하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> isNicknameOk(@RequestBody UserNicknamePostReq userNicknamePostReq, Authentication authentication) {
        return ResponseEntity.ok(UserNicknamePostRes.builder()
                .statusCode(200)
                .message("Success check nickname duplicated")
                .isOk(!userService.existUserNickname(userNicknamePostReq.getUserNickname()))
                .build());
    }

    // U-014
    @GetMapping("/event")
    @ApiOperation(value = "사용자 참여 이벤트 목록", notes = "사용자 참여 이벤트 목록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getUserEventList(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(UserGetEventListRes.builder()
                .statusCode(200)
                .message("Success get user event list")
                .events(userEventService.getUserEventList(userId))
                .build());
    }
}
