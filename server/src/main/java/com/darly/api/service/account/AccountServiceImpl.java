package com.darly.api.service.account;

import com.darly.api.request.account.AccountLoginGetReq;
import com.darly.api.request.account.AccountSigninPostReq;
import com.darly.api.response.account.GoogleUserRes;
import com.darly.api.response.account.KakaoUserRes;
import com.darly.db.entity.User;
import com.darly.db.entity.userAddress.UserAddress;
import com.darly.db.entity.userAddress.UserAddressId;
import com.darly.db.repository.account.AccountRepository;
import com.darly.db.repository.userAddress.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service("accountService")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserAddressRepository userAddressRepository;
    private final WebClient webClient;

    @Override
    public Optional<User> getUserByUserEmail(String userEmail) {
        return accountRepository.findUserByUserEmail(userEmail);
    }

    @Override
    public String kakaoLogin(AccountLoginGetReq accountLoginPostReq) {
        KakaoUserRes kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me") // KAKAO의 유저 정보 받아오는 url
//                .headers(h -> h.setBearerAuth(accountLoginPostReq.getTokenId())) // JWT 토큰을 Bearer 토큰으로 지정
                .header("Authorization", "Bearer " + accountLoginPostReq.getTokenId())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new RuntimeException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserRes.class)
                .block();
        System.out.println(kakaoUserResponse);
        return (boolean)kakaoUserResponse.getKakao_account().get("has_email") ? (String)kakaoUserResponse.getKakao_account().get("email") : kakaoUserResponse.getId().toString();
    }

    @Override
    public String googleLogin(AccountLoginGetReq accountLoginPostReq) {
        GoogleUserRes googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accountLoginPostReq.getTokenId()).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new RuntimeException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(GoogleUserRes.class)
                .block();

        return googleUserResponse.getEmail();
    }

    @Override
    public User createUser(String userEmail) {
        return accountRepository.save(User.ofEmailAndName(userEmail, userEmail));
    }

    @Override
    public boolean updateUserNicknameAndAddress(Long userId, AccountSigninPostReq accountSigninPostReq) {
        Optional<User> opUser = accountRepository.findById(userId);
        if (!opUser.isPresent())
            return false;
        User user = opUser.get();
        user.setUserNickname(accountSigninPostReq.getUserNickname());
        accountRepository.save(user);
        for (Long addressId : accountSigninPostReq.getAddresses()) {
            userAddressRepository.save(new UserAddress(UserAddressId.builder().addressId(addressId).userId(userId).build()));
        }
        return true;
    }
}
