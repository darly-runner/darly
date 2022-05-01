package com.darly.api.controller;

import com.darly.api.request.feed.FeedUpdatePatchReq;
import com.darly.api.response.feed.FeedDetailGetRes;
import com.darly.api.service.comment.CommentService;
import com.darly.api.service.feed.FeedImageService;
import com.darly.api.service.feed.FeedService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.feed.Feed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{feedId}")
    @ApiOperation(value = "피드 수정", notes = "피드 수정하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 feedId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> updateFeed(@PathVariable("feedId") Long feedId, @ModelAttribute FeedUpdatePatchReq feedUpdatePatchReq, Authentication authentication) {
        if(!feedService.updateFeed(feedId, feedUpdatePatchReq))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail update feed: Not valid feedId"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success update feed"));
    }

    @DeleteMapping("/{feedId}")
    @ApiOperation(value = "피드 삭제", notes = "피드 삭제하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 feedId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteFeed(@PathVariable("feedId") Long feedId, @ModelAttribute FeedUpdatePatchReq feedUpdatePatchReq, Authentication authentication) {
        if(!feedService.deleteByFeedId(feedId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail delete feed: Not valid feedId"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success delete feed"));
    }

}
