package com.ashik.WhatsApp.Service;

import com.ashik.WhatsApp.DTOs.SendMessageReq;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.ChatException;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Utils.Message;

import java.util.List;

public interface MessgaeService {

    public Message SendMessage(SendMessageReq req) throws UserException, ChatException;

    public List<Message> getChatMessage(Integer chatId, User reguser) throws ChatException, UserException;

    public Message findMessageById(Integer mssgId) throws UserException;

    public void deleteMessage(Integer mssgId, User reguser) throws UserException;


}
