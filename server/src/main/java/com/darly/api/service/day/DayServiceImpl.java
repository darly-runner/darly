package com.darly.api.service.day;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.response.stat.StatWeekGetRes;
import com.darly.db.entity.day.Day;
import com.darly.db.repository.day.DayRepository;
import com.darly.db.repository.day.DayRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service("dayService")
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository dayRepository;
    private final DayRepositorySupport dayRepositorySupport;

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

    @Override
    public StatWeekGetRes getWeekStats(Long userId, String date) {
        float[] distances = new float[7];
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = startDate.plusDays(7);
        List<Day> dayList = dayRepositorySupport.findByStartAndEnd(userId, getTimeStamp(startDate), getTimeStamp(endDate));
        float totalDistance = 0, totalPace = 0;
        int totalTime = 0, totalHeart = 0, totalNum = 0;
        for (Day day : dayList) {
            distances[getIntDayByLongDate(day.getDayDate())] = day.getDayDistance();
            totalDistance += day.getDayDistance();
            totalPace += day.getDayPace();
            totalTime += day.getDayTime();
            totalNum += day.getDayNum();
            if (day.getDayHeart() != null)
                totalHeart += day.getDayHeart();
        }
        return StatWeekGetRes.builder()
                .statusCode(200)
                .message("Success get week statistics")
                .totalDistance(totalDistance)
                .totalNum(totalNum)
                .totalTime(totalTime)
                .paceAvg(totalNum == 0 ? totalPace : (totalPace / totalNum))
                .heartAvg((totalNum == 0 | totalHeart == 0) ? null : (totalHeart / totalNum))
                .distances(distances)
                .build();
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

    private Long getTimeStamp(LocalDate localDate) {
        return Timestamp.valueOf(localDate.atStartOfDay()).getTime() / 1000;
    }

    private int getIntDayByLongDate(Long date) {
        Timestamp timestamp = new Timestamp(date * 1000);
        return timestamp.toLocalDateTime().getDayOfWeek().getValue() - 1;
    }
}
