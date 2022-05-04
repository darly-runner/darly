package com.darly.api.controller;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.socket.ChatRoom;
import com.darly.db.entity.socket.ChatRoomForm;
import com.darly.db.repository.match.MatchRepository;
import com.darly.db.repository.socket.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {
    private final MatchRepository matchRepository;

//    @GetMapping
//    public String rooms(Model model){
//        model.addAttribute("rooms",chatRoomRepository.findAllRoom());
//        return "rooms";
//    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable Long id, Model model){
//        ChatRoom room = chatRoomRepository.findRoomById(id);
//        model.addAttribute("room",room);

        Match match = matchRepository.findByMatchId(id);
        model.addAttribute("room", match);

        return "room";
    }

    @GetMapping("/new/{crewId}")
    public String make(Model model,@PathVariable String crewId){
//        ChatRoomForm form = new ChatRoomForm();
//        model.addAttribute("form",form);

        Long id = Long.parseLong(crewId);
        model.addAttribute("matchCreatePostReq",new MatchCreatePostReq());
        model.addAttribute("crewId", id);
        model.addAttribute("authorization", "1");
        return "newRoom";
    }

}
