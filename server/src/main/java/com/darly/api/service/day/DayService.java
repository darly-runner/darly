package com.darly.api.service.day;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.response.stat.StatGetRes;
import com.darly.db.entity.day.Day;

public interface DayService {
    Day saveToday(Long userId, RecordCreatePostReq recordCreatePostReq);
    StatGetRes getWeekStats(Long userId, String date);
    StatGetRes getMonthStats(Long userId, String date);
    StatGetRes getYearStats(Long userId, String date);
}
