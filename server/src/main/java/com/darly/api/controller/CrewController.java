package com.darly.api.controller;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.api.service.crew.CrewAddressService;
import com.darly.api.service.crew.CrewService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.Crew;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crew")
public class CrewController {

    private final CrewService crewService;
    private final CrewAddressService crewAddressService;

    @PostMapping
    public ResponseEntity<? extends BaseResponseBody> createCrew(@RequestBody CrewCreatePostReq crewCreatePostReq, Authentication authentication) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        Crew crew = crewService.createCrew(userId, crewCreatePostReq);
        if (crew == null)
            return ResponseEntity.ok(BaseResponseBody.of(405, "Fail save crew: Not valid userId or crewJoin"));
        if(!crewAddressService.createCrewAddress(crew.getCrewId(), crewCreatePostReq.getCrewAddress()))
            return ResponseEntity.ok(BaseResponseBody.of(406, "Fail save crew: Not valid addressId"));
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success save crew"));
    }
}
