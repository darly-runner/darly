package com.darly.db.repository.account;

import com.darly.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserEmail(String userEmail);
}
