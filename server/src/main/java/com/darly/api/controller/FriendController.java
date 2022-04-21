package com.darly.api.controller;

import com.darly.api.response.friend.FriendListGetRes;
import com.darly.api.service.friend.FriendService;
import com.darly.api.service.user.UserService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.friend.FriendTitleMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

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


}
