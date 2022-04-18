package com.darly.api.service.Account;

import com.darly.db.entity.User;
import com.darly.db.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User getUserByUserEmail(String userEmail) {
        return accountRepository.findUserByUserEmail(userEmail).orElse(null);
    }
}
