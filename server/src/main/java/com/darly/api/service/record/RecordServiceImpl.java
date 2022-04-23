package com.darly.api.service.record;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.db.entity.record.Record;
import com.darly.db.repository.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service("recordSevice")
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public Record createRecord(Long userId, Long dayId, RecordCreatePostReq recordCreatePostReq) {
        return recordRepository.save(Record.builder()
                .userId(userId)
                .dayId(dayId)
                .matchId(recordCreatePostReq.getMatchId())
                .recordDate(getTimestamp())
                .recordDistance(recordCreatePostReq.getRecordDistance())
                .recordTime(recordCreatePostReq.getRecordTime())
                .recordPace(recordCreatePostReq.getRecordPace())
                .recordSpeed(recordCreatePostReq.getRecordSpeed())
                .recordHeart(recordCreatePostReq.getRecordHeart())
                .recordStartLatitude(recordCreatePostReq.getRecordStartLatitude())
                .recordStartLongitude(recordCreatePostReq.getRecordStartLongitude())
                .recordEndLatitude(recordCreatePostReq.getRecordEndLatitude())
                .recordEndLongitude(recordCreatePostReq.getRecordEndLongitude())
                .recordCalories(recordCreatePostReq.getRecordCalories())
                .recordRank(recordCreatePostReq.getRecordRank())
                .build());
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now()).getTime() / 1000;
    }
}
