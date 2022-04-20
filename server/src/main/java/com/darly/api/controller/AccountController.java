package com.darly.api.controller;

import com.darly.api.request.account.AccountLoginGetReq;
import com.darly.api.request.account.AccountSigninPostReq;
import com.darly.api.response.account.AccountLoginPostRes;
import com.darly.api.service.account.AccountService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.common.util.JwtTokenUtil;
import com.darly.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController{

    private final AccountService accountService;

    //A-001
    @GetMapping("/kakao")
    public ResponseEntity<? extends BaseResponseBody> kakaoLogin(@RequestBody AccountLoginGetReq accountLoginGetReq){
        String userEmail = accountService.kakaoLogin(accountLoginGetReq);
        return ResponseEntity.ok(getResponseByEmail(userEmail));
    }

    //A-002
    @GetMapping("/google")
    public ResponseEntity<? extends BaseResponseBody> googleLogin(@RequestBody AccountLoginGetReq accountLoginGetReq){
        String userEmail = accountService.googleLogin(accountLoginGetReq);
        return ResponseEntity.ok(getResponseByEmail(userEmail));
    }

    public AccountLoginPostRes getResponseByEmail(String userEmail){
        Optional<User> opUser = accountService.getUserByUserEmail(userEmail);
        if(opUser.isPresent())
            return AccountLoginPostRes.of(200, "Login Successful", JwtTokenUtil.getToken(opUser.get().getUserId()));
        else{
            User user = accountService.createUser(userEmail);
            return AccountLoginPostRes.of(201, "Login Successful: First login", JwtTokenUtil.getToken(user.getUserId()));
        }
    }

    //A-003
    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> signin(@RequestBody AccountSigninPostReq accountSigninPostReq, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if(accountService.updateUserNicknameAndAddress(userId, accountSigninPostReq))
            return ResponseEntity.ok(BaseResponseBody.of(200, "Signin Successful"));
        else
            return ResponseEntity.ok(BaseResponseBody.of(405, "Signin Fail : Not valid userId"));
    }
}
