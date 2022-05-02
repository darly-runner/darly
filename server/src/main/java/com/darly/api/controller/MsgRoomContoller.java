package com.darly.api.controller;

import com.darly.api.service.socket.MsgService;
import com.darly.db.entity.socket.MsgRoom;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comm")
public class MsgRoomContoller {
    private final MsgService msgService;

    @GetMapping("/room")
    public String room(Model model) {
        return "/comm/room";
    }

    @GetMapping("/rooms")
    public List<MsgRoom> rooms() {
        return msgService.findAllRoom();
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomEnter(Model model, @ApiParam(value="방 ID", required = true) @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "comm/roomEnter";
    }

    @GetMapping("/room/{roomId}")
    public MsgRoom roomInfo(@ApiParam(value="방 ID", required=true)@PathVariable String roomId) {
        return msgService.findById(roomId);
    }

    @PostMapping("/room")
    @ResponseBody
    public MsgRoom createRoom(@RequestParam String name) {
        return msgService.createRoom(name);
    }
}
