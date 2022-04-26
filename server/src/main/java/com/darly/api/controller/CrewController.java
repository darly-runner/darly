package com.darly.api.controller;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.api.request.crew.GetCrewSearchModel;
import com.darly.api.response.crew.CrewSearchGetRes;
import com.darly.api.service.crew.CrewAddressService;
import com.darly.api.service.crew.CrewService;
import com.darly.api.service.crew.UserCrewService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewTitleMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crew")
public class CrewController {

    private final CrewService crewService;
    private final UserCrewService userCrewService;
    private final CrewAddressService crewAddressService;

    // C-001
    @PostMapping
    public ResponseEntity<? extends BaseResponseBody> createCrew(@RequestBody CrewCreatePostReq crewCreatePostReq, Authentication authentication) {
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
}