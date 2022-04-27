package com.darly.api.controller;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.api.request.crew.GetCrewSearchModel;
import com.darly.api.response.crew.CrewDetailGetRes;
import com.darly.api.response.crew.CrewMyGetRes;
import com.darly.api.response.crew.CrewSearchGetRes;
import com.darly.api.service.crew.CrewAddressService;
import com.darly.api.service.crew.CrewService;
import com.darly.api.service.crew.UserCrewService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewDetailMapping;
import com.darly.db.entity.crew.CrewTitleMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Crew Api", tags = "Crew")
@RestController
@RequiredArgsConstructor
@RequestMapping("/crew")
public class CrewController {

    private final CrewService crewService;
    private final UserCrewService userCrewService;
    private final CrewAddressService crewAddressService;

    // C-001
    @PostMapping
    @ApiOperation(value = "크루생성", notes = "크루생성하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "테스트 성공"),
            @ApiResponse(code = 404, message = "잘못된 url 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<? extends BaseResponseBody> createCrew(@ModelAttribute CrewCreatePostReq crewCreatePostReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
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
        Long userId = Long.parseLong((String) authentication.getPrincipal());
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
        Long userId = Long.parseLong((String) authentication.getPrincipal());
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
}