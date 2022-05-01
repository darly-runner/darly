package com.darly.api.controller;

import com.darly.api.request.comment.CommentUpdatePatchReq;
import com.darly.api.response.feed.FeedDetailGetRes;
import com.darly.api.service.comment.CommentService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(value = "Comment Api", tags = "Comment")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong((String) authentication.getPrincipal());
    }

    // CM-001
    @PatchMapping("/{commentId}")
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 commentId"),
            @ApiResponse(code = 406, message = "userId != hostId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdatePatchReq commentUpdatePatchReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Comment> commentOptional = commentService.getComment(commentId);
        if(!commentOptional.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail update comment: Not valid commentId"));
        Comment comment = commentOptional.get();
        if(!userId.equals(comment.getUser().getUserId()))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail update comment: User is not host"));
        commentService.updateComment(comment, commentUpdatePatchReq.getCommentContent());
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success update comment"));
    }
}
