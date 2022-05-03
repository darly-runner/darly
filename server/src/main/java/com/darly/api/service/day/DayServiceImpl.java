package com.darly.api.service.day;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.response.stat.StatAllGetRes;
import com.darly.api.response.stat.StatGetRes;
import com.darly.db.entity.day.Day;
import com.darly.db.entity.user.User;
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
        Optional<Day> opDay = dayRepository.findByUser_UserIdAndDayDate(userId, today);
        if (opDay.isPresent()) {
            Day day = opDay.get();
            day.setDayDistance(day.getDayDistance() + recordCreatePostReq.getRecordDistance());
            day.setDayNum(day.getDayNum() + 1);
            day.setDayPace(day.getDayPace() + recordCreatePostReq.getRecordPace());
            day.setDayTime(day.getDayTime() + recordCreatePostReq.getRecordTime());
            if (recordCreatePostReq.getRecordHeart() != 0) {
                day.setDayHeart(day.getDayHeart() + recordCreatePostReq.getRecordHeart());
                day.setDayHeartNum(day.getDayHeartNum() + 1);
            }
            return dayRepository.save(day);
        }
        return dayRepository.save(Day.builder()
                .user(User.builder().userId(userId).build())
                .dayDate(today)
                .dayDistance(recordCreatePostReq.getRecordDistance())
                .dayNum(1)
                .dayPace(recordCreatePostReq.getRecordPace())
                .dayTime(recordCreatePostReq.getRecordTime())
                .dayHeart(recordCreatePostReq.getRecordHeart())
                .dayHeartNum(recordCreatePostReq.getRecordHeart() == null ? 0 : 1)
                .build());
    }

    @Override
    public StatGetRes getWeekStats(Long userId, String date) {
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = startDate.plusDays(7);
        float[] distances = new float[7];
        List<Day> dayList = dayRepositorySupport.findByStartAndEnd(userId, getTimeStamp(startDate), getTimeStamp(endDate));
        float totalDistance = 0, totalPace = 0;
        int totalTime = 0, totalHeart = 0, totalNum = 0, totalHeartNum = 0;
        for (Day day : dayList) {
            distances[getWeekDayByLongDate(day.getDayDate())] = day.getDayDistance();
            totalDistance += day.getDayDistance();
            totalPace += day.getDayPace();
            totalTime += day.getDayTime();
            totalNum += day.getDayNum();
            if (day.getDayHeart() != 0) {
                totalHeart += day.getDayHeart();
                totalHeartNum += day.getDayHeartNum();
            }
        }
        return StatGetRes.builder()
                .statusCode(200)
                .message("Success get week statistics")
                .totalDistance(totalDistance)
                .totalNum(totalNum)
                .totalTime(totalTime)
                .paceAvg(totalNum == 0 ? totalPace : (totalPace / totalNum))
                .heartAvg(totalHeartNum == 0 ? null : (totalHeart / totalNum))
                .distances(distances)
                .build();
    }

    @Override
    public StatGetRes getMonthStats(Long userId, String date) {
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE).withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1);
        float[] distances = new float[startDate.lengthOfMonth()];
        List<Day> dayList = dayRepositorySupport.findByStartAndEnd(userId, getTimeStamp(startDate), getTimeStamp(endDate));
        float totalDistance = 0, totalPace = 0;
        int totalTime = 0, totalHeart = 0, totalNum = 0, totalHeartNum = 0;
        for (Day day : dayList) {
            distances[getMonthDayByLongDate(day.getDayDate())] = day.getDayDistance();
            totalDistance += day.getDayDistance();
            totalPace += day.getDayPace();
            totalTime += day.getDayTime();
            totalNum += day.getDayNum();
            if (day.getDayHeart() != 0) {
                totalHeart += day.getDayHeart();
                totalHeartNum += day.getDayHeartNum();
            }
        }
        return StatGetRes.builder()
                .statusCode(200)
                .message("Success get month statistics")
                .totalDistance(totalDistance)
                .totalNum(totalNum)
                .totalTime(totalTime)
                .paceAvg(totalNum == 0 ? totalPace : (totalPace / totalNum))
                .heartAvg(totalHeartNum == 0 ? null : (totalHeart / totalNum))
                .distances(distances)
                .build();
    }

    @Override
    public StatGetRes getYearStats(Long userId, String date) {
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE).withMonth(1).withDayOfMonth(1);
        LocalDate endDate = startDate.plusYears(1);
        float[] distances = new float[12];
        List<Day> dayList = dayRepositorySupport.findByStartAndEnd(userId, getTimeStamp(startDate), getTimeStamp(endDate));
        float totalDistance = 0, totalPace = 0;
        int totalTime = 0, totalHeart = 0, totalNum = 0, totalHeartNum = 0;
        for (Day day : dayList) {
            distances[getMonthByLongDate(day.getDayDate())] += day.getDayDistance();
            totalDistance += day.getDayDistance();
            totalPace += day.getDayPace();
            totalTime += day.getDayTime();
            totalNum += day.getDayNum();
            if (day.getDayHeart() != 0) {
                totalHeart += day.getDayHeart();
                totalHeartNum += day.getDayHeartNum();
            }
        }
        return StatGetRes.builder()
                .statusCode(200)
                .message("Success get year statistics")
                .totalDistance(totalDistance)
                .totalNum(totalNum)
                .totalTime(totalTime)
                .paceAvg(totalNum == 0 ? totalPace : (totalPace / totalNum))
                .heartAvg(totalHeartNum == 0 ? null : (totalHeart / totalNum))
                .distances(distances)
                .build();
    }

    @Override
    public StatAllGetRes getAllStats(Long userId) {
        List<Day> dayList = dayRepository.findByUser_UserId(userId);
        int startYear = getYearByLongDate(dayList.get(0).getDayDate());
        int endYear = getYearByLongDate(dayList.get(dayList.size() - 1).getDayDate());
        float[] distances = new float[endYear - startYear + 1];
        float totalDistance = 0, totalPace = 0;
        int totalTime = 0, totalHeart = 0, totalNum = 0, totalHeartNum = 0;
        for (Day day : dayList) {
            distances[getYearByLongDate(day.getDayDate()) - startYear] += day.getDayDistance();
            totalDistance += day.getDayDistance();
            totalPace += day.getDayPace();
            totalTime += day.getDayTime();
            totalNum += day.getDayNum();
            if (day.getDayHeart() != 0) {
                totalHeart += day.getDayHeart();
                totalHeartNum += day.getDayHeartNum();
            }
        }
        return StatAllGetRes.builder()
                .statusCode(200)
                .message("Success get all statistics")
                .totalDistance(totalDistance)
                .totalNum(totalNum)
                .totalTime(totalTime)
                .paceAvg(totalNum == 0 ? totalPace : (totalPace / totalNum))
                .heartAvg(totalHeartNum == 0 ? null : (totalHeart / totalNum))
                .startYear(startYear)
                .distances(distances)
                .build();
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

    private Long getTimeStamp(LocalDate localDate) {
        return Timestamp.valueOf(localDate.atStartOfDay()).getTime() / 1000;
    }

    private int getWeekDayByLongDate(Long date) {
        Timestamp timestamp = new Timestamp(date * 1000);
        return timestamp.toLocalDateTime().getDayOfWeek().getValue() - 1;
    }

    private int getMonthDayByLongDate(Long date) {
        Timestamp timestamp = new Timestamp(date * 1000);
        return timestamp.toLocalDateTime().getDayOfMonth() - 1;
    }

    private int getMonthByLongDate(Long date) {
        Timestamp timestamp = new Timestamp(date * 1000);
        return timestamp.toLocalDateTime().getMonth().getValue() - 1;
    }

    private int getYearByLongDate(Long date) {
        Timestamp timestamp = new Timestamp(date * 1000);
        return timestamp.toLocalDateTime().getYear();
    }
}
