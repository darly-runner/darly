package com.darly.api.service.account;

import com.darly.db.entity.User;

public interface AccountService {
    User getUserByUserEmail(String userEmail);
}
