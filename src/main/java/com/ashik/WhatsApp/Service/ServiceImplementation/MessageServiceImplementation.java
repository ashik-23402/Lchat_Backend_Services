package com.ashik.WhatsApp.Service.ServiceImplementation;

import com.ashik.WhatsApp.DTOs.SendMessageReq;
import com.ashik.WhatsApp.Entities.Chat;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.ChatException;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Repository.MessageRepository;
import com.ashik.WhatsApp.Service.ChatService;
import com.ashik.WhatsApp.Service.MessgaeService;
import com.ashik.WhatsApp.Service.UserService;
import com.ashik.WhatsApp.Utils.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class MessageServiceImplementation implements MessgaeService {

    private MessageRepository messageRepository;
    private UserService userService;
    private ChatService chatService;

    public MessageServiceImplementation(MessageRepository messageRepository,
                                        UserService userService, ChatService chatService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Message SendMessage(SendMessageReq req) throws UserException, ChatException {

        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setContent(req.getContent());
        message.setUser(user);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);

        return message;
    }

    @Override
    public List<Message> getChatMessage(Integer chatId,User reguser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        if(!chat.getUsers().contains(reguser)){
            throw  new UserException("login na kore message dekhte chaccho???");
        }
        List<Message>mssges = messageRepository.findByChatId(chat.getId());


        return mssges;
    }

    @Override
    public Message findMessageById(Integer mssgId) throws UserException {
        Optional<Message> msg = messageRepository.findById(mssgId);

        if(msg.isPresent()){
            return  msg.get();
        }
        throw new UserException("message pawa jay nai");
    }

    @Override
    public void deleteMessage(Integer mssgId,User reguser) throws UserException {

        Message msg = findMessageById(mssgId);

        if(msg.getUser().getId().equals(reguser.getId())){
            messageRepository.deleteById(mssgId);
        }

        throw new UserException("you cant delete mesage ");

    }
}
