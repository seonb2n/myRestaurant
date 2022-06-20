package com.example.myrestaurant.common.chat.model;

import com.example.myrestaurant.common.util.TokenGenerator;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {

    private String roomId;
    private String roomName;
    private String location;

    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = TokenGenerator.randomCharacterWithPrefix("chatRoom_");
        room.roomName = name;
        return room;
    }
}