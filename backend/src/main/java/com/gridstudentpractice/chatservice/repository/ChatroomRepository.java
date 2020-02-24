package com.gridstudentpractice.chatservice.repository;

import com.gridstudentpractice.chatservice.model.Chatroom;

import java.util.List;

public interface ChatroomRepository {

    boolean createChatroom(Chatroom chatroom);
    Chatroom getChatroomById(int chatroomId);
    List<Chatroom> getChatroomByName(String chatroomName);

}
