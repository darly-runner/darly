package com.darly.api.service.day;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.db.entity.day.Day;

import java.util.Optional;

public interface DayService {
    Day saveToday(Long userId, RecordCreatePostReq recordCreatePostReq);
}
