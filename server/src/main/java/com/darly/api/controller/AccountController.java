package com.darly.api.controller;

import com.darly.api.request.account.AccountLoginPostReq;
import com.darly.api.response.account.AccountLoginPostRes;
import com.darly.api.service.Account.AccountService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.common.util.JwtTokenUtil;
import com.darly.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController{

    private final AccountService accountService;

    //A-001
    @GetMapping("/kakao")
    public ResponseEntity<? extends BaseResponseBody> kakaoLogin(@RequestBody AccountLoginPostReq accountPostReq){

        return ResponseEntity.ok(AccountLoginPostRes.of(200, "Login Successful"));
//        return ResponseEntity.ok(AccountPostRes.of(200, "Success Login", JwtTokenUtil.getToken(user.get().getUserId())));
//        Optional<User> user = accountService.getUserByUserEmail(accountPostReq.getUserEmail());
//        if(user.isPresent())
//            return ResponseEntity.ok(AccountPostRes.of(200, "Success Login", JwtTokenUtil.getToken(user.get().getUserId())));
//        else
//            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail Login: Not valid userEmail"));
    }

    @GetMapping("/google")
    public ResponseEntity<? extends BaseResponseBody> googleLogin(@RequestBody AccountLoginPostReq accountLoginPostReq){
        Long userId = accountService.googleLogin(accountLoginPostReq);
        System.out.println("userId" + userId);
        return ResponseEntity.ok(AccountLoginPostRes.of(200, "Login Successful", JwtTokenUtil.getToken(userId)));
    }

    //accessToken 갱신
    @GetMapping("/refresh")
    public ResponseEntity<? extends BaseResponseBody> refreshToken(){
//        String accessToken = JwtTokenUtil.getToken()
        return ResponseEntity.ok(AccountLoginPostRes.of(200, "Login Successful"));
    }

    @GetMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> success(@RequestParam("email")String email){
        return ResponseEntity.ok(AccountLoginPostRes.of(200, "Success Login"));//, JwtTokenUtil.getToken(userId)));
    }
}
