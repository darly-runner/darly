package com.darly.api.service.Account;

import com.darly.api.request.account.AccountLoginPostReq;
import com.darly.api.response.account.GoogleUserRes;
import com.darly.api.response.account.KakaoUserRes;
import com.darly.db.entity.User;
import com.darly.db.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        KakaoUserRes kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me") // KAKAO의 유저 정보 받아오는 url
                .headers(h -> h.setBearerAuth(accountLoginPostReq.getTokenId())) // JWT 토큰을 Bearer 토큰으로 지정
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
//                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoUserRes.class)
                .block();

        User user = accountRepository.findUserByUserEmail(kakaoUserResponse.getEmail()).orElse(accountRepository.save(kakaoUserResponse.toEntity()));
        return user.getUserId();
    }

    @Override
    public Long googleLogin(AccountLoginPostReq accountLoginPostReq) {
        GoogleUserRes googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accountLoginPostReq.getTokenId()).build())
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
//                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserRes.class)
                .block();

        User user = accountRepository.findUserByUserEmail(googleUserResponse.getEmail()).orElse(accountRepository.save(googleUserResponse.toEntity()));
        return user.getUserId();
    }
}
