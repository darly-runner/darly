package com.darly.api.service.record;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.record.RecordMapping;
import com.darly.db.entity.user.User;
import com.darly.db.repository.record.RecordRepository;
import com.darly.db.repository.record.RecordRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service("recordSevice")
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordRepositorySupport recordRepositorySupport;

    @Override
    public Record createRecord(Long userId, Long dayId, RecordCreatePostReq recordCreatePostReq) {
        LocalDateTime today = LocalDateTime.now();
        String title = recordCreatePostReq.getRecordTitle() == null ? getTitle(today) : recordCreatePostReq.getRecordTitle();
        return recordRepository.save(Record.builder()
                .user(User.builder().userId(userId).build())
                .dayId(dayId)
                .matchId(recordCreatePostReq.getMatchId())
                .recordTitle(title)
                .recordDate(getTimestamp(today))
                .recordDistance(recordCreatePostReq.getRecordDistance())
                .recordTime(recordCreatePostReq.getRecordTime())
                .recordPace(recordCreatePostReq.getRecordPace())
                .recordSpeed(recordCreatePostReq.getRecordSpeed())
                .recordHeart(recordCreatePostReq.getRecordHeart())
                .recordCalories(recordCreatePostReq.getRecordCalories())
                .recordRank(recordCreatePostReq.getRecordRank())
                .build());
    }

    @Override
    public List<RecordMapping> getRecordListAll(Long userId) {
        return recordRepositorySupport.getRecordListAll(userId);
    }

    @Override
    public List<RecordMapping> getRecordListTop(Long userId) {
        return recordRepositorySupport.getRecordListTop(userId);
    }

    @Override
    public Record getRecordDetail(Long recordId) {
        return recordRepository.findByRecordId(recordId);
    }

    @Override
    public void updateRecord(Record record) {
        recordRepository.save(record);
    }

    private Long getTimestamp(LocalDateTime today) {
        return Timestamp.valueOf(today).getTime() / 1000;
    }

    private String getTitle(LocalDateTime today) {
        String title = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        if (today.getHour() < 12)
            title += " 오전 러닝";
        else
            title += " 오후 러닝";
        return title;
    }
}
