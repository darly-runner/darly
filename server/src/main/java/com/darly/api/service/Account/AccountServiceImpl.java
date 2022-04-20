package com.darly.api.service.Account;

import com.darly.api.request.account.AccountLoginPostReq;
import com.darly.api.response.account.GoogleUserResponse;
import com.darly.db.entity.User;
import com.darly.db.repository.AccountRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WebClient webClient;

    @Override
    public Optional<User> getUserByUserEmail(String userEmail) {
        return accountRepository.findUserByUserEmail(userEmail);
    }

    @Override
    public Long kakaoLogin(AccountLoginPostReq accountLoginPostReq) {
//        User user =
        return null;
    }

    @Override
    public Long googleLogin(AccountLoginPostReq accountLoginPostReq) {
        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accountLoginPostReq.getGoogleCode()).build())
                // KAKAO와 달리 GOOGLE을 IdToken을 query parameter로 받습니다. 이로 인해 KAKAO와 uri 작성 방식이 상이합니다.
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
//                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        System.out.println(googleUserResponse);

        User user = accountRepository.findUserByUserEmail(googleUserResponse.getEmail()).orElse(accountRepository.save(googleUserResponse.toEntity()));

        return user.getUserId();
    }
}
