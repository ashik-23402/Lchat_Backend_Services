package com.ashik.WhatsApp.Controllers;

import com.ashik.WhatsApp.DTOs.SendMessageReq;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.ChatException;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Response.ApiResponse;
import com.ashik.WhatsApp.Service.MessgaeService;
import com.ashik.WhatsApp.Service.UserService;
import com.ashik.WhatsApp.Utils.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/protect")
public class MessageController {

    private MessgaeService messgaeService;
    private UserService userService;

    public MessageController(MessgaeService messgaeService, UserService userService) {
        this.messgaeService = messgaeService;
        this.userService = userService;
    }

    @PostMapping("/sendmessage")
    public ResponseEntity<Message>sendMessage(@RequestBody SendMessageReq req,
                                              @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messgaeService.SendMessage(req);

        return new ResponseEntity<Message>(message, HttpStatus.OK);

    }

    @GetMapping ("chat/{chatId}/messages")
    public ResponseEntity<List<Message>>getChatMesaage(@PathVariable Integer chatId,
                                              @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);

        List<Message> message = messgaeService.getChatMessage(chatId,user);

        return new ResponseEntity<List<Message>>(message, HttpStatus.OK);

    }


    @DeleteMapping ("delete/{messsageId}")
    public ResponseEntity<ApiResponse>deleteMesaage(@PathVariable Integer messsageId,
                                                    @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);

       messgaeService.deleteMessage(messsageId,user);

       ApiResponse apiResponse = new ApiResponse("deleted successfully",true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);


    }




}
