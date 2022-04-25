package com.darly.db.repository.day;

import com.darly.db.entity.day.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findByUserIdAndDayDate(Long userId, Long dayDate);
}
