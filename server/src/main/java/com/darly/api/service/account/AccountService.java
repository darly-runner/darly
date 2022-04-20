package com.darly.api.service.account;

import com.darly.api.request.account.AccountLoginGetReq;
import com.darly.api.request.account.AccountSigninPostReq;
import com.darly.db.entity.User;

import java.util.Optional;

public interface AccountService {
    Optional<User> getUserByUserEmail(String userEmail);
    String kakaoLogin(AccountLoginGetReq accountLoginPostReq);
    String googleLogin(AccountLoginGetReq accountLoginPostReq);
    User createUser(String userEmail);
    boolean updateUserNicknameAndAddress(Long userId, AccountSigninPostReq accountSigninPostReq);
}
