package com.darly.api.controller;

import com.darly.api.request.crew.*;
import com.darly.api.request.feed.FeedCreatePostReq;
import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.response.crew.*;
import com.darly.api.response.match.MatchListGetRes;
import com.darly.api.service.crew.*;
import com.darly.api.service.feed.FeedImageService;
import com.darly.api.service.feed.FeedService;
import com.darly.api.service.match.MatchService;
import com.darly.api.service.match.UserMatchService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.common.util.Type;
import com.darly.db.entity.crew.*;
import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.match.Match;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(value = "Crew Api", tags = "Crew")
@RestController
@RequiredArgsConstructor
@RequestMapping("/crew")
public class CrewController {

    private final CrewService crewService;
    private final UserCrewService userCrewService;
    private final CrewWaitingService crewWaitingService;
    private final FeedService feedService;
    private final FeedImageService feedImageService;
    private final MatchService matchService;
    private final UserMatchService userMatchService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong((String) authentication.getPrincipal());
    }

    // C-001
    @PostMapping
    @ApiOperation(value = "크루생성", notes = "크루생성하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 userId 혹은 crewJoin"),
            @ApiResponse(code = 406, message = "잘못된 addressId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> createCrew(@ModelAttribute CrewCreatePostReq crewCreatePostReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Crew crew = crewService.createCrew(userId, crewCreatePostReq);
        if (crew == null)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail save crew: Not valid userId or crewJoin"));
        if (!userCrewService.createUserCrew(userId, crew.getCrewId()))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail save crew: Not valid userId or crewJoin"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success save crew"));
    }

    // C-002
    @GetMapping
    @ApiOperation(value = "크루목록", notes = "크루목록얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getCrewSearchList(GetCrewSearchModel getCrewSearchModel, Authentication authentication) {
        Long userId = getUserId(authentication);
        List<CrewTitleMapping> crewList;
        Long crewCount;
        if (getCrewSearchModel.getAddress() != 0 && getCrewSearchModel.getAddress() != null) {
            crewList = crewService.getCrewSearchListByAddressAndKey(userId, getCrewSearchModel.getAddress(), getCrewSearchModel.getKey(), getCrewSearchModel.getSize(), getCrewSearchModel.getPage() * getCrewSearchModel.getSize());
            crewCount = crewService.getCrewCountByAddressAndKey(userId, getCrewSearchModel.getAddress(), getCrewSearchModel.getKey());
        } else {
            crewList = crewService.getCrewSearchListByKey(userId, getCrewSearchModel.getKey(), getCrewSearchModel.getSize(), getCrewSearchModel.getPage() * getCrewSearchModel.getSize());
            System.out.println((getCrewSearchModel.getPage() + ", " + getCrewSearchModel.getPage() * getCrewSearchModel.getSize()));
            crewCount = crewService.getCrewCountByKey(userId, getCrewSearchModel.getKey());
        }
        return ResponseEntity.ok(CrewSearchGetRes.builder()
                .statusCode(200)
                .message("Success get crew search list")
                .currentPage(getCrewSearchModel.getPage())
                .crewCount(crewCount)
                .crewList(crewList)
                .size(getCrewSearchModel.getSize())
                .build());
    }

    // C-003
    @GetMapping("/my")
    @ApiOperation(value = "내크루목록", notes = "내크루목록얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getMyCrewList(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(CrewMyGetRes.builder()
                .statusCode(200)
                .message("Success get crew search list")
                .crew(userCrewService.getMyCrewList(userId))
                .build());
    }

    // C-004
    @GetMapping("/{crewId}")
    @ApiOperation(value = "크루 상세정보", notes = "크루 상세정보 얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getCrewDetail(@PathVariable("crewId") Long crewId, Authentication authentication) {
        List<CrewDetailMapping> crewDetailList = crewService.getCrewDetailByCrewId(crewId);
        if (crewDetailList.size() == 0)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get crew detail: Not valid crewId"));
        return ResponseEntity.ok(CrewDetailGetRes.builder()
                .statusCode(200)
                .message("Success get crew detail")
                .crewDetailMapping(crewDetailList.get(0))
                .build());
    }

    // C-005
    @PutMapping("/{crewId}")
    @ApiOperation(value = "크루 설정변경", notes = "크루 설정변경하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> updateCrew(@PathVariable("crewId") Long crewId, @ModelAttribute CrewUpdatePutReq crewUpdatePutReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail update crew: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail update crew: User is not host"));
        crewService.updateCrew(crew.get(), crewUpdatePutReq);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success update crew"));
    }

    // C-006
    @PatchMapping("/{crewId}")
    @ApiOperation(value = "크루 공지변경", notes = "크루 공지변경하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> updateCrewNotice(@PathVariable("crewId") Long crewId, @RequestBody CrewUpdatePatchReq crewUpdatePatchReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail update crew notice: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail update crew notice: User is not host"));
        crewService.updateCrewNotice(crew.get(), crewUpdatePatchReq);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success update crew notice"));
    }

    // C-007
    @DeleteMapping("/{crewId}")
    @ApiOperation(value = "크루 탈퇴", notes = "크루 탈퇴하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "호스트의 탈퇴요청"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> leaveCrew(@PathVariable("crewId") Long crewId, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail leave crew: Not valid crewId"));
        if (crew.get().getUser().getUserId().equals(userId)) {
            Long userNum = userCrewService.countUserNum(crewId);
            if (userNum == 1) {
                userCrewService.leaveCrew(crewId, userId);
                crewService.deleteCrew(crewId);
                return ResponseEntity.ok(BaseResponseBody.of(201, "Success delete crew"));
            }
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail leave crew: Host can't leave crew"));
        }
        userCrewService.leaveCrew(crewId, userId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success leave crew"));
    }

    // C-008
    @PatchMapping("/{crewId}/mandate")
    @ApiOperation(value = "크루 위임", notes = "크루 위임하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> mandateCrew(@PathVariable("crewId") Long crewId, @RequestBody CrewMandatePatchReq crewMandatePatchReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail mandate crew: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail mandate crew: User is not host"));
        crewService.updateCrewHost(crew.get(), crewMandatePatchReq);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success mandate crew"));
    }

    // C-009
    @PostMapping("/{crewId}/join")
    @ApiOperation(value = "크루 가입신청", notes = "크루 가입신청하기")
    @ApiResponses({           
            @ApiResponse(code = 200, message = "테스트 성공, 대기중(crew Lock)"),
            @ApiResponse(code = 201, message = "테스트 성공, 바로가입(crew Free)"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "이미 크루원"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> joinCrew(@PathVariable("crewId") Long crewId, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail join crew: Not valid crewId"));
        if (userCrewService.isUserCrewExists(userId, crewId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail join crew: Already crew"));
        if (crew.get().getCrewJoin() == Type.Lock.getLabel()) {
            userCrewService.createUserCrew(userId, crewId);
            return ResponseEntity.ok(BaseResponseBody.of(201, "Success join crew"));
        }
        crewWaitingService.createCrewWaiting(userId, crewId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success apply crew"));
    }

    // C-010
    @PostMapping("/{crewId}/accept")
    @ApiOperation(value = "크루 가입허가", notes = "크루 가입허가하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 407, message = "신청이 없음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> acceptCrew(@PathVariable("crewId") Long crewId, @RequestBody CrewApplyReq crewApplyReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail accept crew: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail accept crew: User is not host"));
        Optional<CrewWaiting> crewWaiting = crewWaitingService.getCrewWaiting(crewApplyReq.getUserId(), crewId);
        if (!crewWaiting.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(407, "Fail accept crew: No apply"));
        crewWaitingService.deleteByCrewWaiting(crewWaiting.get());
        userCrewService.createUserCrew(crewApplyReq.getUserId(), crewId);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success accept crew"));
    }

    // C-011
    @DeleteMapping("/{crewId}/deny")
    @ApiOperation(value = "크루 가입거절", notes = "크루 가입거절하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 407, message = "신청이 없음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> denyCrew(@PathVariable("crewId") Long crewId, @RequestBody CrewApplyReq crewApplyReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail deny crew: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail deny crew: User is not host"));
        Optional<CrewWaiting> crewWaiting = crewWaitingService.getCrewWaiting(crewApplyReq.getUserId(), crewId);
        if (!crewWaiting.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(407, "Fail deny crew: No apply"));
        crewWaitingService.deleteByCrewWaiting(crewWaiting.get());
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success deny crew"));
    }

    // C-012
    @GetMapping("/{crewId}/waiting")
    @ApiOperation(value = "크루 가입대기원 목록", notes = "크루 가입대기원 목록 얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getCrewWaitingList(@PathVariable("crewId") Long crewId, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get waiting list: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail get waiting list: User is not host"));
        return ResponseEntity.ok(CrewWaitingGetRes.builder()
                .statusCode(200)
                .message("Success get waiting list")
                .users(crewWaitingService.getCrewWaitingList(crewId))
                .build());
    }

    // C-013
    @GetMapping("/{crewId}/people")
    @ApiOperation(value = "크루원 목록", notes = "크루원 목록 얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 호스트가아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getUserCrewList(@PathVariable("crewId") Long crewId, Authentication authentication) {
        Long userId = getUserId(authentication);
        Optional<Crew> crew = crewService.getCrewByCrewId(crewId);
        if (!crew.isPresent())
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get crew user list: Not valid crewId"));
        if (!crew.get().getUser().getUserId().equals(userId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail get crew user list: User is not host"));
        return ResponseEntity.ok(CrewPeopleGetRes.builder()
                .statusCode(200)
                .message("Success get crew user list")
                .users(userCrewService.getCrewPeopleList(crewId))
                .build());
    }

    // C-014
    @GetMapping("/{crewId}/summary")
    @ApiOperation(value = "크루 요약", notes = "크루 요약 얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "잘못된 type(week, month, all중 하나여야함)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getCrewSummary(@PathVariable("crewId") Long crewId, @RequestParam(name = "type") String type, Authentication authentication) {
        if (!crewService.isCrewExists(crewId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get crew summary: Not valid crewId"));
        List<CrewSummaryMapping> crewSummaryMappingList = userCrewService.getCrewSummaryList(crewId, type);
        if (crewSummaryMappingList == null)
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail get crew summary: Not valid type"));
        return ResponseEntity.ok(CrewSummaryGetRes.builder()
                .statusCode(200)
                .message("Success get crew summary")
                .summaryList(crewSummaryMappingList)
                .build());
    }

    // C-015
    @GetMapping("/{crewId}/feed")
    @ApiOperation(value = "크루 피드목록", notes = "크루 피드목록 얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> getCrewFeedList(@PathVariable("crewId") Long crewId, Pageable page, Authentication authentication) {
        if (!crewService.isCrewExists(crewId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get crew feed list: Not valid crewId"));
        return ResponseEntity.ok(CrewFeedGetRes.builder()
                .statusCode(200)
                .page(feedService.getFeedList(crewId, page))
                .currentPage(page.getPageNumber())
                .message("Success get crew feed list")
                .build());
    }

    // C-016
    @PostMapping("/{crewId}/feed")
    @ApiOperation(value = "크루 피드 생성", notes = "크루 피드 생성하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 크루원이 아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> createCrewFeed(@PathVariable("crewId") Long crewId, @ModelAttribute FeedCreatePostReq feedCreatePostReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        if (!crewService.isCrewExists(crewId))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail create crew feed: Not valid crewId"));
        if (!userCrewService.isUserCrewExists(userId, crewId))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail create crew feed: User is not member"));
        Feed feed = feedService.createFeed(userId, crewId, feedCreatePostReq.getFeedTitle(), feedCreatePostReq.getFeedContent());
        feedImageService.createFeedImage(feed.getFeedId(), feedCreatePostReq.getFeedImages());
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success create crew feed"));
    }

    // C-017
    @GetMapping("/{crewId}/match")
    @ApiOperation(value = "크루 경쟁방 목록", notes = "크루 경쟁방 목록 얻기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
//    public ResponseEntity<? extends BaseResponseBody> getCrewMatchList(@PathVariable("crewId") Long crewId, Pageable page, Authentication authentication) {
      public ModelAndView getCrewMatchList(@PathVariable("crewId") Long crewId, Pageable page) {
//        if (!crewService.isCrewExists(crewId))
//            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail get crew match list: Not valid crewId"));
//        return ResponseEntity.ok(MatchListGetRes.builder()
//                .statusCode(200)
//                .message("Success get crew match list")
//                .page(matchService.getCrewMatchList(crewId, page))
//                .currentPage(page.getPageNumber())
//                .build());

        // 프론트 연동 테스트
       ModelAndView modelAndView = new ModelAndView("rooms");
        modelAndView.addObject("result", MatchListGetRes.builder()
                .statusCode(200)
                .message("Success get crew match list")
                .page(matchService.getCrewMatchList(crewId, page))
                .currentPage(page.getPageNumber())
                .build());

        return modelAndView;
    }

    // C-018
    @PostMapping("/{crewId}/match")
    @ApiOperation(value = "크루 경쟁방 생성", notes = "크루원 경쟁방 만들기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 405, message = "잘못된 crewId"),
            @ApiResponse(code = 406, message = "권한 없음(유저가 크루원이 아님)"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    //public ResponseEntity<? extends BaseResponseBody> createCrewMatch(@PathVariable("crewId") Long crewId, @RequestBody MatchCreatePostReq matchCreatePostReq, Authentication authentication) {
    public ModelAndView createCrewMatch(@PathVariable("crewId") Long crewId, MatchCreatePostReq matchCreatePostReq) {
//        Long userId = getUserId(authorization);
//        if (!crewService.isCrewExists(crewId))
//            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail create crew match: Not valid crewId"));
//        if (!userCrewService.isUserCrewExists(userId, crewId))
//            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail create crew match: User is not member"));
//        Match match = matchService.createCrewMatch(crewId, userId, matchCreatePostReq);
//        userMatchService.createUserMatch(userId, match.getMatchId());
//        return ResponseEntity.ok(BaseResponseBody.of(200, "Success create crew match"));

        // 테스트용
        Long userId = Long.parseLong(matchCreatePostReq.getAuthorization());
        Match match = matchService.createCrewMatch(crewId, userId, matchCreatePostReq);
        userMatchService.createUserMatch(userId, match.getMatchId());

        return new ModelAndView("redirect:/crew/"+crewId+"/match");
    }
}