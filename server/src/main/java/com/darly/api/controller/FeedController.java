package com.darly.api.controller;

import com.darly.api.service.feed.FeedService;
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
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Feed Api", tags = "Feed")
@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong((String) authentication.getPrincipal());
    }

    // FD-001
    @GetMapping("/{feedId}")
    @ApiOperation(value = "피드 상세", notes = "피드 상세보기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getFeedDetail(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success save crew"));
    }
}
