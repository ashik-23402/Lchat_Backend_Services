package com.ashik.WhatsApp.Service;

import com.ashik.WhatsApp.DTOs.GroupChatRequest;
import com.ashik.WhatsApp.Entities.Chat;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.ChatException;
import com.ashik.WhatsApp.Exceptions.UserException;

import java.util.List;

public interface ChatService {

    public Chat createChat(User regUser, Integer userId2, boolean isGroup) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

    public Chat addUserTogroup(Integer userId, Integer chatId) throws UserException,ChatException;

    public Chat renameGroup(Integer chatid, String groupName) throws ChatException,UserException;

    public Chat removeFromGroup(Integer chatid, Integer userId) throws UserException,ChatException;
    public  void deleteChat(Integer chatId, Integer userId) throws ChatException,UserException;


}
