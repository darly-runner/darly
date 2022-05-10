package com.darly.api.controller;

import com.darly.api.request.event.EventPatchReq;
import com.darly.api.request.event.EventPostReq;
import com.darly.api.response.event.EventGetRes;
import com.darly.api.response.event.EventsGetRes;
import com.darly.api.service.event.EventService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.event.EventList;
import com.darly.db.entity.event.EventOne;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 이벤트에 대한 명령 컨트롤러
 * */
@Api(value="이벤트 API", tags= {"Events"})
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;

    // 1. 이벤트 목록 List GET
    @GetMapping
    @ApiOperation(value="이벤트 목록 조회", notes="이벤트 목록 List 조회")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<EventsGetRes> getEvents() {
        List<EventList> eventList = eventService.getEventList();

        return ResponseEntity.ok(EventsGetRes.of(eventList,200, "success"));
    }

    // 2. 이벤트 조회 1개 GET
    @GetMapping("/get")
    @ApiOperation(value="이벤트 1개 조회", notes="이벤트 조회")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<EventGetRes> getEvent(Long eventId) {
        EventOne eventOne = eventService.getEvent(eventId);

        return ResponseEntity.ok(EventGetRes.of(eventOne, 200, "success"));
    }

    // 3. 이벤트 생성 POST
    @PostMapping
    @ApiOperation(value="이벤트 생성", notes="이벤트 생성")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> postEvent(@ModelAttribute EventPostReq eventPostReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        eventService.createEvent(eventPostReq, userId);

        return ResponseEntity.ok(BaseResponseBody.of(200,"success"));
    }

    // 4. 이벤트 수정 PATCH
    @PatchMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value="이벤트 수정", notes="eventTitle, eventContent, eventImage 수정")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> patchEvent(@ModelAttribute EventPatchReq eventPatchReq, Long eventId) {
        System.out.println(eventPatchReq.getEventImage().getOriginalFilename());
        eventService.patchEvent(eventPatchReq, eventId);

        return ResponseEntity.ok(BaseResponseBody.of( 200,"success"));
    }


    // 5. 이벤트 삭제 DELETE
    @DeleteMapping
    @ApiOperation(value="이벤트 삭제", notes="이벤트 삭제")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> deleteEvent(Long eventId) {
        eventService.deleteEvent(eventId);

        return ResponseEntity.ok(BaseResponseBody.of( 200,"success"));
    }
}
