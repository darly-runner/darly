package com.darly.api.controller;

import com.darly.common.model.response.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    // F-001
    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getFriendList(Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());

        return ResponseEntity.ok(BaseResponseBody.of(200, "Success get friend list"));
    }

}
