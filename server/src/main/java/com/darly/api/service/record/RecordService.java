package com.darly.api.service.record;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.record.RecordMapping;

import java.util.List;

public interface RecordService {
    Record createRecord(Long userId, Long dayId, RecordCreatePostReq recordCreatePostReq);
    List<RecordMapping> getRecordListAll(Long userId);
    List<RecordMapping> getRecordListTop(Long userId);
    Record getRecordDetail(Long recordId);
    void updateRecord(Record record);
}
