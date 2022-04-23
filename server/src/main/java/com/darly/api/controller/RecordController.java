package com.darly.api.controller;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.service.day.DayService;
import com.darly.api.service.record.CoordinateService;
import com.darly.api.service.record.RecordService;
import com.darly.api.service.record.SectionService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.day.Day;
import com.darly.db.entity.record.Coordinate;
import com.darly.db.entity.record.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private final DayService dayService;
    private final RecordService recordService;
    private final SectionService sectionService;
    private final CoordinateService coordinateService;

    // R-001
    @PostMapping
    public ResponseEntity<? extends BaseResponseBody> createRecord(@RequestBody RecordCreatePostReq recordCreatePostReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        Record record = recordService.createRecord(userId, dayService.saveToday(userId, recordCreatePostReq).getDayId(), recordCreatePostReq);
        sectionService.createSection(record.getRecordId(), recordCreatePostReq.getSections());
        coordinateService.createCoordinate(record.getRecordId(), recordCreatePostReq.getCoordinateLatitudes(), recordCreatePostReq.getCoordinateLongitudes());
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success save record"));
    }
}
