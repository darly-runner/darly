package com.darly.db.repository.record;

import com.darly.db.entity.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByRecordId(Long recordId);
    Long countAllByUser_UserId(Long userId);
}
