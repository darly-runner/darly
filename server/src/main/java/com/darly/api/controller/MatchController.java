package com.darly.api.controller;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.db.entity.socket.ChatRoom;
import com.darly.db.entity.socket.ChatRoomForm;
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
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping
    public String rooms(Model model){
        model.addAttribute("rooms",chatRoomRepository.findAllRoom());
        return "rooms";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model){
        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room",room);
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

    @PostMapping("/room/new")
    public String makeRoom(ChatRoomForm form){
        chatRoomRepository.createChatRoom(form.getName());

        return "redirect:/chat";
    }
}
