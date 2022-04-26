package com.darly.api.controller;


import com.darly.api.request.user.UserPatchConditionReq;
import com.darly.api.request.user.UserPatchReq;
import com.darly.api.request.user.UserPostFeedReq;
import com.darly.api.response.user.UserGetBadgeListRes;
import com.darly.api.response.user.UserStatsGetRes;
import com.darly.api.service.user.UserService;
import com.darly.api.response.user.UserGetRes;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.badge.Badge;
import com.darly.db.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * 유저에 대한 명령 컨트롤러
 * /users
 */
@Api(value ="유저 API", tags = {"User"})
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    // 1. 유저 정보 조회 GET
    @GetMapping
    @ApiOperation(value="유저정보조회", notes="userNickname, userEmail, userPoint, userImage를 가져옴")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<UserGetRes> getUser(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        User user = userService.getUserByUserId(userId);

        return ResponseEntity.ok(UserGetRes.of(user, 200, "success"));
    }

    // 2. 유저정보수정 PATCH
    @PatchMapping
    @ApiOperation(value="유저정보수정", notes="userNickname, userImage 수정")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> patchUser(UserPatchReq userPatchReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userService.patchUser(userPatchReq, userId);

        return ResponseEntity.ok(BaseResponseBody.of(200, "success"));
    }

    // 3. 총누적정보조회 GET /stats
    @GetMapping("/stats")
    @ApiOperation(value="유저총누적정보조회", notes="userTotalDistance, userToTalTime, " +
            "userToTalHeart, userHeartNum, userMinPace를 가져옴")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<UserStatsGetRes> getUserStats(Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        User user = userService.getUserByUserId(userId);

        return ResponseEntity.ok(UserStatsGetRes.of(user, 200, "success"));
    }

    // 4. 뱃지목록조회 GET /badge
    @GetMapping("/badge")
    @ApiOperation(value="유저뱃지조회", notes="현재 유저가 보유한 badge의 badgeName, badgeImage, badgeCondition을 가져옴")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<UserGetBadgeListRes> getBadge(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        List<Badge> badgeList = userService.getBadgeList(userId);

        return ResponseEntity.ok(UserGetBadgeListRes.of(badgeList, 200, "success"));
    }

    // 5. 거리/시간수정 PATCH /condition
    @PatchMapping("/condition")
    @ApiOperation(value="목표거리,시간수정", notes="userGoalDistance, userGoalTime 수정")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> patchUserCondition(UserPatchConditionReq userPatchConditionReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userService.patchUserCondition(userPatchConditionReq, userId);

        return ResponseEntity.ok(BaseResponseBody.of(200, "success"));
    }

    // 6. 지역정보조회 GET /address

    // 7. 지역정보수정 PUT /address

    // 8. 유저피드작성 POST /feed
    @PostMapping("/feed")
    @ApiOperation(value="유저피드작성", notes="feedImage 작성")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> postUserFeed(UserPostFeedReq userPostFeedReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        userService.postUserFeed(userPostFeedReq, userId);


        return ResponseEntity.ok(BaseResponseBody.of(200, "message"));
    }
    // 9. 유저피드목록 GET /feed?page

    // 10. 유저피드삭제 DELETE /feed/{feedId}

    // 11. 유저피드수정 PATCH /feed/{feedId}
}
