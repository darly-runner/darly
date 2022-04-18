package com.darly.api.service.Account;

import com.darly.db.entity.User;
import org.springframework.stereotype.Service;

public interface AccountService {
    User getUserByUserEmail(String userEmail);
}
