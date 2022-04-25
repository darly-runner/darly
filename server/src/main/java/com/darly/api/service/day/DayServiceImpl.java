package com.darly.api.service.day;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.db.entity.day.Day;
import com.darly.db.repository.day.DayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Service("dayService")
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository dayRepository;

    @Override
    public Day saveToday(Long userId, RecordCreatePostReq recordCreatePostReq) {
        Long today = getTimestamp();
        System.out.println("today");
        System.out.println(today);
        Optional<Day> opDay = dayRepository.findByUserIdAndDayDate(userId, today);
        if (opDay.isPresent()) {
            Day day = opDay.get();
            day.setDayDistance(day.getDayDistance() + recordCreatePostReq.getRecordDistance());
            day.setDayNum(day.getDayNum() + 1);
            day.setDayPace(day.getDayPace() + recordCreatePostReq.getRecordPace());
            day.setDayTime(day.getDayTime() + recordCreatePostReq.getRecordTime());
            return dayRepository.save(day);
        }
        return dayRepository.save(Day.builder()
                .userId(userId)
                .dayDate(today)
                .dayDistance(recordCreatePostReq.getRecordDistance())
                .dayNum(1)
                .dayPace(recordCreatePostReq.getRecordPace())
                .dayTime(recordCreatePostReq.getRecordTime())
                .build());
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }
}
