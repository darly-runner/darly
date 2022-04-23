package com.darly.api.service.record;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.db.entity.record.Record;

public interface RecordService {
    Record createRecord(Long userId, Long dayId, RecordCreatePostReq recordCreatePostReq);
}
