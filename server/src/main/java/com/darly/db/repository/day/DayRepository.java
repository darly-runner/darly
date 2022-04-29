package com.darly.db.repository.day;

import com.darly.db.entity.day.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findByUser_UserIdAndDayDate(Long userId, Long dayDate);
    List<Day> findByUser_UserId(Long userId);
}
