package com.darly.api.controller;

import com.darly.api.request.crew.*;
import com.darly.api.response.crew.CrewDetailGetRes;
import com.darly.api.response.crew.CrewMyGetRes;
import com.darly.api.response.crew.CrewSearchGetRes;
import com.darly.api.service.crew.CrewAddressService;
import com.darly.api.service.crew.CrewService;
import com.darly.api.service.crew.CrewWaitingService;
import com.darly.api.service.crew.UserCrewService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.common.util.Type;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewDetailMapping;
import com.darly.db.entity.crew.CrewTitleMapping;
import com.darly.db.entity.crew.CrewWaiting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "Crew Api", tags = "Crew")
@RestController
@RequiredArgsConstructor
@RequestMapping("/crew")
public class CrewController {

    private final CrewService crewService;
    private final UserCrewService userCrewService;
    private final CrewAddressService crewAddressService;
    private final CrewWaitingService crewWaitingService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong((String) authentication.getPrincipal());
    }

    // C-001
    @PostMapping
    @ApiOperation(value = "크루생성", notes = "크루생성하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> createCrew(@ModelAttribute CrewCreatePostReq crewCreatePostReq, Authentication authentication) {
        Long userId = getUserId(authentication);
        Crew crew = crewService.createCrew(userId, crewCreatePostReq);
        if (crew == null)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail save crew: Not valid userId or crewJoin"));
        if (!userCrewService.createUserCrew(userId, crew.getCrewId()))
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail save crew: Not valid userId or crewJoind"));
        if (!crewAddressService.createCrewAddress(crew.getCrewId(), crewCreatePostReq.getCrewAddress()))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail save crew: Not valid addressId"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success save crew"));
    }

    // C-002
    @GetMapping
    @ApiOperation(value = "크루목록", notes = "크루목록 가져오기")
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
    public ResponseEntity<? extends BaseResponseBody> getMyCrewList(@PathVariable("crewId") Long crewId, Authentication authentication) {
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
    public ResponseEntity<? extends BaseResponseBody> getCrewWaitingList(@PathVariable("crewId") Long crewId, Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success deny crew"));
    }

}