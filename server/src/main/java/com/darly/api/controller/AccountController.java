package com.darly.api.controller;

import com.darly.api.request.account.AccountLoginGetReq;
import com.darly.api.request.account.AccountSigninPostReq;
import com.darly.api.response.account.AccountLoginPostRes;
import com.darly.api.service.account.AccountService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.common.util.JwtTokenUtil;
import com.darly.db.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(value="accout Api", tags = {"Accounts"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController{

    private final AccountService accountService;

    //A-001
    @PostMapping("/kakao")
    @ApiOperation(value="카카오로그인", notes="카카오로 로그인하기")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> kakaoLogin(@RequestBody AccountLoginGetReq accountLoginGetReq){
        String userEmail = accountService.kakaoLogin(accountLoginGetReq);
        return ResponseEntity.ok(getResponseByEmail(userEmail));
    }

    //A-002
    @PostMapping("/google")
    @ApiOperation(value="구글로그인", notes="구글로 로그인하기")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> googleLogin(@RequestBody AccountLoginGetReq accountLoginGetReq){
        String userEmail = accountService.googleLogin(accountLoginGetReq);
        return ResponseEntity.ok(getResponseByEmail(userEmail));
    }

    public AccountLoginPostRes getResponseByEmail(String userEmail){
        Optional<User> opUser = accountService.getUserByUserEmail(userEmail);
        if(opUser.isPresent())
            return AccountLoginPostRes.of(200, "Success login", JwtTokenUtil.getToken(opUser.get().getUserId()));
        else{
            User user = accountService.createUser(userEmail);
            return AccountLoginPostRes.of(201, "Success login: First login", JwtTokenUtil.getToken(user.getUserId()));
        }
    }

    //A-003
    @PatchMapping
    @ApiOperation(value="회원가입", notes="회원가입(지역, 닉네임 입력)")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> signin(@RequestBody AccountSigninPostReq accountSigninPostReq, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if(accountService.updateUserNicknameAndAddress(userId, accountSigninPostReq))
            return ResponseEntity.ok(BaseResponseBody.of(200, "Success signin"));
        else
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail signin : Not valid userId"));
    }
}
