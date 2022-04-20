package com.darly.api.controller;

import com.darly.api.request.event.EventPostReq;
import com.darly.api.response.event.EventsGetRes;
import com.darly.api.service.event.EventService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.Event;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Event> eventList = eventService.getEventList();

        return ResponseEntity.ok(EventsGetRes.of(eventList, 200, "success"));
    }

    // 2. 이벤트 조회 1개 GET

    // 3. 이벤트 생성 POST
    @PostMapping
    @ApiOperation(value="이벤트 생성", notes="이벤트 생성")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<BaseResponseBody> postEvent(EventPostReq eventPostReq, Long userId) {
        eventService.createEvent(eventPostReq, userId);

        return ResponseEntity.ok(BaseResponseBody.of(200,"success"));
    }
    // 4. 이벤트 수정 PATCH

    // 5. 이벤트 삭제 DELETE
}
