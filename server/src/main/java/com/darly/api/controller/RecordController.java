package com.darly.api.controller;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.response.record.RecordListGetRes;
import com.darly.api.service.day.DayService;
import com.darly.api.service.record.CoordinateService;
import com.darly.api.service.record.RecordService;
import com.darly.api.service.record.SectionService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.record.RecordMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // R-002
    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getRecordList(@RequestParam String type, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if (type.equals("all"))
            return ResponseEntity.ok(RecordListGetRes.builder().statusCode(200).message("Success get record list all").records(recordService.getRecordListAll(userId)).build());
        else if (type.equals("top"))
            return ResponseEntity.ok(RecordListGetRes.builder().statusCode(200).message("Success get record list top").records(recordService.getRecordListTop(userId)).build());
        return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get record list: Not valid type"));
    }
}
