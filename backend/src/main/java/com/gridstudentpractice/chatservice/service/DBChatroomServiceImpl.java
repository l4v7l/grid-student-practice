package com.gridstudentpractice.chatservice.service;

import com.gridstudentpractice.chatservice.model.Chatroom;
import com.gridstudentpractice.chatservice.model.User;
import com.gridstudentpractice.chatservice.repository.ChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBChatroomServiceImpl implements ChatroomService {

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Override
    public void createChatroom(Chatroom chatroom) {
        chatroomRepository.createChatroom(chatroom);
    }

    @Override
    public Chatroom getChatroomById(int chatroomId) {
        return chatroomRepository.getChatroomById(chatroomId);
    }

    @Override
    public List<Chatroom> getChatroomByName(String chatroomName) {
        return chatroomRepository.getChatroomByName(chatroomName);
    }

    @Override
    public void createUserInChatroom(User user, Chatroom chatroom) {
        chatroomRepository.createUserInChatroom(user, chatroom);
    }
}