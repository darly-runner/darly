package com.darly.api.controller;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.response.match.MatchInRes;
import com.darly.api.service.match.MatchService;
import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.repository.match.MatchRepository;
import com.darly.db.repository.match.UserMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private final MatchService matchService;

    // M-001 방 정보 수정 : matchTitle, matchMaxPerson, matchGoalDistance 수정
//    @PatchMapping("/{matchId}")
//    public ResponseEntity<BaseResponseBody> patchMatchInfo(@PathVariable("matchId") Long matchId, MatchPatchReq matchPatchReq){
//        matchService.patchMatchInfo(matchId, matchPatchReq);
//
//        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
//    }

    // M-002 방 삭제 : 나간 사람이 방장(게임시작전)이거나 curperson이 0이 되면 방 상태만 무효화 <- 이 처리는 방 퇴장에서
//    @DeleteMapping


    // M-003 방 참가 : 참가한 인원을 UserMatch 테이블에 등록, curPerson++, matchId에 해당하는 방의 모든 유저를 갖고옴
    @GetMapping("/{matchId}/in")
    public ResponseEntity<MatchInRes> getMatchInfo(@PathVariable("matchId") Long matchId, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        MatchInRes matchInRes = matchService.getMatchInfo(matchId, userId);

        return ResponseEntity.ok(matchInRes);
    }

    @GetMapping("/{matchId}/refresh")
    public ResponseEntity<MatchInRes> getMatchRefresh(@PathVariable("matchId") Long matchId, Authentication authentication){
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        MatchInRes matchInRes = matchService.getMatchRefresh(matchId, userId);

        return ResponseEntity.ok(matchInRes);
    }

//    // M-004 방 퇴장 : 인원을 UserMatch 테이블에서 삭제, curPerson--
//    @DeleteMapping("{matchId}/out")
//    public ResponseEntity<BaseResponseBody> matchOut(@PathVariable("matchId") Long matchId, Authentication authentication) {
//        Long userId = Long.parseLong((String) authentication.getPrincipal());
//        matchService.matchOut(matchId, userId);
//
//        return ResponseEntity.ok(BaseResponseBody.of(200,"success"));
//    }

//    @GetMapping("/rooms/{id}")
//    public String room(@PathVariable Long id, Model model){
//        Match match = matchRepository.findByMatchId(id);
//        model.addAttribute("room", match);
//        UserMatch userMatch = userMatchRepository.findAllByUserMatchId_Match_MatchId(id);
//        model.addAttribute("userMatch", userMatch);
//
//        return "room";
//    }
//
//    @GetMapping("/new/{crewId}")
//    public String make(Model model,@PathVariable String crewId){
//        Long id = Long.parseLong(crewId);
//        model.addAttribute("matchCreatePostReq",new MatchCreatePostReq());
//        model.addAttribute("crewId", id);
//        model.addAttribute("authorization", "1");
//        return "newRoom";
//    }

}
