package com.darly.api.controller;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.request.record.RecordUpdatePatchReq;
import com.darly.api.response.record.RecordDetailGetRes;
import com.darly.api.response.record.RecordListGetRes;
import com.darly.api.service.day.DayService;
import com.darly.api.service.match.MatchResultService;
import com.darly.api.service.record.CoordinateService;
import com.darly.api.service.record.RecordService;
import com.darly.api.service.record.SectionService;
import com.darly.api.service.user.UserService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(value = "Record Api", tags = {"Records"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private final DayService dayService;
    private final UserService userService;
    private final RecordService recordService;
    private final SectionService sectionService;
    private final CoordinateService coordinateService;
    private final MatchResultService matchResultService;

    // R-001
    @PostMapping
    @ApiOperation(value = "경로기록저장", notes = "경로기록저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> createRecord(@ModelAttribute RecordCreatePostReq recordCreatePostReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        Record record = recordService.createRecord(userId, dayService.saveToday(userId, recordCreatePostReq).getDayId(), recordCreatePostReq);
        sectionService.createSection(record.getRecordId(), recordCreatePostReq.getSections());
        coordinateService.createCoordinate(record.getRecordId(), recordCreatePostReq.getCoordinateLatitudes(), recordCreatePostReq.getCoordinateLongitudes());
        userService.updateUserRecord(userId, recordCreatePostReq);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success save record!"));
    }

    // R-002
    @PostMapping("/watch")
    @ApiOperation(value = "경로기록저장_워치용", notes = "경로기록저장_워치용")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> createRecordWatch(@RequestBody RecordCreatePostReq recordCreatePostReq, Authentication authentication) {
        return createRecord(recordCreatePostReq, authentication);
    }

    // R-003
    @GetMapping
    @ApiOperation(value = "경로기록목록", notes = "경로기록목록 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getRecordList(@RequestParam String type, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if (type.equals("all"))
            return ResponseEntity.ok(RecordListGetRes.builder().statusCode(200).message("Success get record list all").records(recordService.getRecordListAll(userId)).build());
        else if (type.equals("top"))
            return ResponseEntity.ok(RecordListGetRes.builder().statusCode(200).message("Success get record list top").records(recordService.getRecordListTop(userId)).build());
        return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get record list: Not valid type"));
    }

    // R-004
    @GetMapping("/{recordId}")
    @ApiOperation(value = "경로기록상세보기", notes = "경로기록1개 상세보기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getRecordDetail(@PathVariable("recordId") Long recordId, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        User user = userService.getUserByUserId(userId);
        Record record = recordService.getRecordDetail(recordId);
        if (record == null)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get record detail: Not valid recordId"));
        return ResponseEntity.ok(RecordDetailGetRes.builder()
                .statusCode(200)
                .message("Success get record detail")
                .userImage(user.getUserImage())
                .record(record)
                .coordinate(coordinateService.getCoordinate(recordId))
                .sections(sectionService.getSectionList(recordId))
                .ranks(record.getMatchId() == null ? null : matchResultService.getMatchResultList(record.getMatchId()))
                .build());
    }

    // R-005
    @PatchMapping("/{recordId}")
    @ApiOperation(value = "경로기록 제목수정", notes = "경로기록 제목수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> updateRecordTitle(@PathVariable("recordId") Long recordId, @RequestBody RecordUpdatePatchReq recordUpdatePatchReq, Authentication authentication) {
        Record record = recordService.getRecordDetail(recordId);
        if (record == null)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail update record title: Not valid recordId"));
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        if (userId != record.getUser().getUserId())
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail update record title: User is not record owner"));
        record.setRecordTitle(recordUpdatePatchReq.getRecordTitle());
        recordService.updateRecord(record);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success update record title"));
    }
}
