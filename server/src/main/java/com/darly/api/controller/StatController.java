package com.darly.api.controller;

import com.darly.api.service.day.DayService;
import com.darly.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
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
    public ResponseEntity<? extends BaseResponseBody> getWeekStats(@RequestParam String date, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getWeekStats(userId, date));
    }

    // S-002
    @GetMapping("/month")
    public ResponseEntity<? extends BaseResponseBody> getMonthStats(@RequestParam String date, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getMonthStats(userId, date));
    }

    // S-003
    @GetMapping("/year")
    public ResponseEntity<? extends BaseResponseBody> getYearStats(@RequestParam String date, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getYearStats(userId, date));
    }

    // S-004
    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getAllStats(Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(dayService.getAllStats(userId));
    }

}
