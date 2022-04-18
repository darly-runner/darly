package com.darly.api.service.User;

import com.darly.db.entity.User;
import com.darly.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserByUserId(int userId) {
        return null;
    }
}
