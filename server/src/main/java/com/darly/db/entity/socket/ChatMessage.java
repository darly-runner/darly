package com.darly.db.entity.socket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType {
       ENTER, CHAT, LEAVE
    }

    private String chatRoomId;
    private String writer;
    private String message;
    private MessageType type;
}
