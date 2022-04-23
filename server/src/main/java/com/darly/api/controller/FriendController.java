package com.darly.api.controller;

import com.darly.api.request.friend.FriendRequestPostReq;
import com.darly.api.response.friend.FriendFeedGetRes;
import com.darly.api.response.friend.FriendListGetRes;
import com.darly.api.response.friend.FriendProfileGetRes;
import com.darly.api.service.friend.FriendService;
import com.darly.api.service.friend.FriendWaitingService;
import com.darly.api.service.user.UserService;
import com.darly.api.service.userAddress.UserAddressService;
import com.darly.api.service.userFeed.UserFeedService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final FriendWaitingService friendWaitingService;
    private final UserAddressService userAddressService;
    private final UserFeedService userFeedService;

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
    public ResponseEntity<? extends BaseResponseBody> requestFriend(@RequestBody FriendRequestPostReq friendRequestPostReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        Long friendId = friendRequestPostReq.getUserId();
        if (friendWaitingService.isFriendWaiting(friendId, userId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail request friend: Already requested"));
        if (friendService.isFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail request friend: Already friend"));
        if (userId.equals(friendId))
            return ResponseEntity.ok(BaseResponseBody.of(407, "Fail request friend: Request to himself"));
        friendWaitingService.createFriendWaiting(friendId, userId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success request friend"));
    }

    // F-004
    @GetMapping("/waiting")
    public ResponseEntity<? extends BaseResponseBody> getFriendWaitingList(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(FriendListGetRes.of(200, "Success get waiting friend list", friendWaitingService.getFriendWaitingList(userId)));
    }

    // F-005
    @PostMapping("/{friendId}/accept")
    public ResponseEntity<? extends BaseResponseBody> acceptFriend(@PathVariable("friendId") Long friendId, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if (!friendWaitingService.deleteFriendWaiting(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail accept friend: No request"));
        if (friendService.isFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail accept friend: Already friend"));
        friendService.createFriend(userId, friendId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success accept friend"));
    }

    // F-006
    @DeleteMapping("/{friendId}/deny")
    public ResponseEntity<? extends BaseResponseBody> denyFriend(@PathVariable("friendId") Long friendId, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if (!friendWaitingService.deleteFriendWaiting(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail deny friend: No request"));
        if (friendService.isFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail deny friend: Already friend"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success deny friend"));
    }

    // F-007
    @DeleteMapping("/{friendId}")
    public ResponseEntity<? extends BaseResponseBody> deleteFriend(@PathVariable("friendId") Long friendId, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if (!friendService.deleteFriend(userId, friendId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail delete friend: Already deleted"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success delete friend"));
    }

    // F-008
    @GetMapping("/{friendId}/profile")
    public ResponseEntity<? extends BaseResponseBody> getFriendProfile(@PathVariable("friendId") Long friendId) {
        User user = userService.getUserByUserId(friendId);
        if (user == null)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get friend profile: Not valid friendId"));
        return ResponseEntity.ok(FriendProfileGetRes.of(200, "Success get friend profile", user, userAddressService.getAddressNameList(friendId), friendService.getFriendNum(friendId)));
    }

    // F-009
    @GetMapping("/{friendId}/feed")
    public ResponseEntity<? extends BaseResponseBody> getFriend(@PathVariable("friendId") Long friendId, @PageableDefault(size = 15, sort = "user_feed_id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.ok(FriendFeedGetRes.builder()
                .page(userFeedService.getUserFeedList(friendId, page))
                .currentPage(page.getPageNumber())
                .statusCode(200)
                .message("Success get friend feed")
                .build());
    }

}
