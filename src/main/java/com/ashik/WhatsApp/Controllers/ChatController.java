package com.ashik.WhatsApp.Controllers;

import com.ashik.WhatsApp.DTOs.GroupChatRequest;
import com.ashik.WhatsApp.DTOs.SingleChatReq;
import com.ashik.WhatsApp.Entities.Chat;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.ChatException;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Response.ApiResponse;
import com.ashik.WhatsApp.Service.ChatService;
import com.ashik.WhatsApp.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/protect")
public class ChatController {

    private ChatService chatService;
    private UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }


    @PostMapping("/singlechat")
    public ResponseEntity<Chat>createChat(@RequestBody SingleChatReq singleChatReq,
                                          @RequestHeader("Authorization") String jwt) throws UserException {

        User requser = userService.findUserProfile(jwt);

        Chat chat = chatService.createChat(requser, singleChatReq.getUserId(),false);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }


    @PostMapping("/groupchat")
    public ResponseEntity<Chat>creategroupChat(@RequestBody GroupChatRequest groupChatRequest,
                                               @RequestHeader("Authorization") String jwt) throws UserException {

        User requser = userService.findUserProfile(jwt);

        Chat chat = chatService.createGroup(groupChatRequest,requser);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);



    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat>findChatbyId(@PathVariable Integer chatId,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ChatException {



        Chat chat = chatService.findChatById(chatId);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);



    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>>findChatbyId(@RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User requser = userService.findUserProfile(jwt);

        List<Chat> chat = chatService.findAllChatByUserId(requser.getId());

        return new ResponseEntity< List<Chat> >(chat, HttpStatus.OK);



    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat>AddUserinChat(@PathVariable Integer chatId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User requser = userService.findUserProfile(jwt);

        Chat chat = chatService.addUserTogroup(userId,chatId);

        return new ResponseEntity< Chat >(chat, HttpStatus.OK);



    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat>RemoveUserinChat(@PathVariable Integer chatId,
                                             @PathVariable Integer userId,
                                             @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User requser = userService.findUserProfile(jwt);

        Chat chat = chatService.removeFromGroup(chatId,userId);

        return new ResponseEntity< Chat >(chat, HttpStatus.OK);



    }

    @DeleteMapping("delete/{chatId}")
    public ResponseEntity<ApiResponse>DeleteChat(@PathVariable Integer chatId,
                                                 @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User requser = userService.findUserProfile(jwt);

         chatService.deleteChat(chatId,requser.getId());
         ApiResponse res = new ApiResponse("deleted successfully",true);

        return new ResponseEntity< ApiResponse >(res, HttpStatus.OK);



    }












}
