package com.darly.api.controller;

import com.darly.api.response.feed.FeedDetailGetRes;
import com.darly.api.service.comment.CommentService;
import com.darly.api.service.feed.FeedImageService;
import com.darly.api.service.feed.FeedService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.comment.Comment;
import com.darly.db.entity.feed.Feed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = "Feed Api", tags = "Feed")
@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;
    private final CommentService commentService;
    private final FeedImageService feedImageService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong((String) authentication.getPrincipal());
    }

    // FD-001
    @GetMapping("/{feedId}")
    @ApiOperation(value = "피드 상세", notes = "피드 상세보기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 feedId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getFeedDetail(@PathVariable("feedId") Long feedId, Authentication authentication) {
        Optional<Feed> feed = feedService.getFeedDetail(feedId);
        if (!feed.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get feed detail: Not valid feedId"));
        return ResponseEntity.ok(FeedDetailGetRes.builder()
                .statusCode(200)
                .message("Success get feed detail")
                .feed(feed.get())
                .commentList(commentService.getCommentList(feedId))
                .feedImages(feedImageService.getFeedImages(feedId))
                .build());
    }
}
