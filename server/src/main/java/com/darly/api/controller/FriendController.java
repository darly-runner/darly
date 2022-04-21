package com.darly.api.controller;

import com.darly.api.request.friend.FriendRequestPostReq;
import com.darly.api.response.friend.FriendListGetRes;
import com.darly.api.service.friend.FriendService;
import com.darly.api.service.friend.FriendWaitingService;
import com.darly.api.service.user.UserService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.friend.FriendTitleMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final FriendWaitingService friendWaitingService;

    // F-001
    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getFriendList(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(FriendListGetRes.of(200, "Success get friend list", friendService.getFriendList(userId)));
    }

    // F-002
    @GetMapping("/{userNickname}")
    public ResponseEntity<? extends BaseResponseBody> searchFriendList(@PathVariable("userNickname") String nickname, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(FriendListGetRes.of(200, "Success get friend search list", userService.getUserSearchList(userId, nickname))); //friendService.getFriendSearchList(userId, nickname)
    }

    // F-003
    @PostMapping
    public ResponseEntity<? extends BaseResponseBody> requestFriend(@RequestBody FriendRequestPostReq friendRequestPostReq, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        Long friendId = friendRequestPostReq.getUserId();
        if(friendWaitingService.isFriendWaiting(friendId, userId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail request friend: Already requested"));
        if(friendService.isFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail request friend: Already friend"));
        if(userId.equals(friendId))
            return ResponseEntity.ok(BaseResponseBody.of(407, "Fail request friend: Request to himself"));
        friendWaitingService.createFriendWaiting(friendId, userId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success request friend"));
    }

    // F-004
    @GetMapping("/waiting")
    public ResponseEntity<? extends BaseResponseBody> getFriendWaitingList(Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(FriendListGetRes.of(200, "Success get waiting friend list", friendWaitingService.getFriendWaitingList(userId)));
    }

    // F-005
    @PostMapping("/{friendId}/accept")
    public ResponseEntity<? extends BaseResponseBody> acceptFriend(@PathVariable("friendId") Long friendId, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if(!friendWaitingService.deleteFriendWaiting(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail accept friend: No request"));
        if(friendService.isFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail accept friend: Already friend"));
        friendService.createFriend(userId, friendId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success accept friend"));
    }

    // F-006
    @DeleteMapping("/{friendId}/deny")
    public ResponseEntity<? extends BaseResponseBody> denyFriend(@PathVariable("friendId") Long friendId, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if(!friendWaitingService.deleteFriendWaiting(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail deny friend: No request"));
        if(friendService.isFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail deny friend: Already friend"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success deny friend"));
    }
}
