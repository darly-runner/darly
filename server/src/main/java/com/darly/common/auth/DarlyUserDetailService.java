package com.darly.common.auth;

import com.darly.api.service.account.AccountService;
import com.darly.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
public class DarlyUserDetailService implements UserDetailsService {
    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = accountService.getUserByUserEmail(username);
        if(user.isPresent()){
            DarlyUserDetails userDetails = new DarlyUserDetails(user.get());
            return userDetails;
        }
        return null;
    }
}
