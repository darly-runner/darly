package com.darly.api.controller;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.response.match.MatchInRes;
import com.darly.api.service.match.MatchService;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.repository.match.MatchRepository;
import com.darly.db.repository.match.UserMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private final MatchService matchService;


    @GetMapping("/{matchId}/in")
    public ResponseEntity<MatchInRes> getMatchInfo(@PathVariable("matchId") Long matchId){
        MatchInRes matchInRes = matchService.getMatchInfo(matchId);

        return ResponseEntity.ok(matchInRes);
    }

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
