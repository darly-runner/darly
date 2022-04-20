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
    public ResponseEntity<? extends BaseResponseBody> kakaoLogin(@RequestBody AccountLoginPostReq accountLoginPostReq){
        return ResponseEntity.ok(AccountLoginPostRes.of(200, "Login Successful", JwtTokenUtil.getToken(accountService.kakaoLogin(accountLoginPostReq))));
    }

    @GetMapping("/google")
    public ResponseEntity<? extends BaseResponseBody> googleLogin(@RequestBody AccountLoginPostReq accountLoginPostReq){
        return ResponseEntity.ok(AccountLoginPostRes.of(200, "Login Successful", JwtTokenUtil.getToken(accountService.googleLogin(accountLoginPostReq))));
    }
}
