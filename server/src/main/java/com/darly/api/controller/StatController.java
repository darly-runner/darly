package com.darly.api.controller;

import com.darly.api.service.day.DayService;
import com.darly.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value="stats Api", tags = {"Stats"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {

    private final DayService dayService;

    // S-001
    @GetMapping("/week")
    @ApiOperation(value="주단위 달린거리 통계", notes="주단위 달린거리 통계")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getWeekStats(@RequestParam String date, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getWeekStats(userId, date));
    }

    // S-002
    @GetMapping("/month")
    @ApiOperation(value="월단위 달린거리 통계", notes="월단위 달린거리 통계")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getMonthStats(@RequestParam String date, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getMonthStats(userId, date));
    }

    // S-003
    @GetMapping("/year")
    @ApiOperation(value="연단위 달린거리 통계", notes="연단위 달린거리 통계")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getYearStats(@RequestParam String date, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getYearStats(userId, date));
    }

    // S-004
    @GetMapping
    @ApiOperation(value="전체 달린거리 통계", notes="전체 달린거리 통계")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getAllStats(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getAllStats(userId));
    }

}
