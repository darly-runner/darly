package com.darly.api.service.User;

import com.darly.db.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUserId(int userId);
}
