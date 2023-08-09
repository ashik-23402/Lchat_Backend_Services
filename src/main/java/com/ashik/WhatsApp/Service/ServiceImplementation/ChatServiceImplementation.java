package com.ashik.WhatsApp.Service.ServiceImplementation;

import com.ashik.WhatsApp.DTOs.GroupChatRequest;
import com.ashik.WhatsApp.Entities.Chat;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.ChatException;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Repository.ChatRepository;
import com.ashik.WhatsApp.Service.ChatService;
import com.ashik.WhatsApp.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImplementation implements ChatService {

    private ChatRepository chatRepository;
    private UserService userService;

    public ChatServiceImplementation(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public Chat createChat(User regUser, Integer userId2, boolean isGroup)
            throws UserException {


        User user = userService.findUserById(userId2);

        Chat isChatExist = chatRepository.findSingleChatByUserIds(user,regUser);

        if(isChatExist != null){
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreateBy(regUser);
        chat.getUsers().add(user);
        chat.getUsers().add(regUser);
        chat.setGroup(false);

        chatRepository.save(chat);



        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if(chat.isPresent()){
            return  chat.get();
        }
        throw new ChatException("CHAT NOT FOUND WITH ID"+ chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        List<Chat> chats = chatRepository.findChatByUserId(user.getId());
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {

        Chat groupChat = new Chat();
        groupChat.setGroup(true);
        groupChat.setChat_image(req.getChat_image());
        groupChat.setChat_name(req.getChat_name());
        groupChat.setCreateBy(reqUser);

        for(Integer uid : req.getUsersId()){
            User user = userService.findUserById(uid);
            groupChat.getUsers().add(user);
        }

        chatRepository.save(groupChat);

        return groupChat;
    }

    @Override
    public Chat addUserTogroup(Integer userId, Integer chatId) throws UserException, ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);

        User user = userService.findUserById(userId);

        if(chat.isPresent()){
            chat.get().getUsers().add(user);
            return  chatRepository.save(chat.get());
        }




        throw new ChatException("Chat not found  chat id "+ chatId);
    }

    @Override
    public Chat renameGroup(Integer chatid, String groupName)
            throws ChatException, UserException {

        Optional<Chat> chat = chatRepository.findById(chatid);

        if(chat.isPresent()){
            chat.get().setChat_name(groupName);
            return chat.get();
        }

        throw new ChatException("chat not found with "+chatid);
    }

    @Override
    public Chat removeFromGroup(Integer chatid, Integer userId)
            throws UserException, ChatException {

        Optional<Chat> chat = chatRepository.findById(chatid);
        User user = userService.findUserById(userId);

        if(chat.isPresent()){
            chat.get().getUsers().remove(user);
            chatRepository.save(chat.get());
            return chat.get();
        }

        throw new ChatException("CHAT NOT FOUND "+chatid);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if(chat.isPresent()){
            chatRepository.delete(chat.get());
        }


    }
}
