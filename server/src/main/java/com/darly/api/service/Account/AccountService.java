package com.darly.api.service.Account;

import com.darly.api.request.account.AccountLoginPostReq;
import com.darly.db.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AccountService {
    Optional<User> getUserByUserEmail(String userEmail);
    Long kakaoLogin(AccountLoginPostReq accountLoginPostReq);
    Long googleLogin(AccountLoginPostReq accountLoginPostReq);
}
