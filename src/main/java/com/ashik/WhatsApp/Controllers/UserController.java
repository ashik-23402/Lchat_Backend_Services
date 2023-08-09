package com.ashik.WhatsApp.Controllers;

import com.ashik.WhatsApp.DTOs.UpdateUserReq;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Response.ApiResponse;
import com.ashik.WhatsApp.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;


    public  UserController(UserService userService){
        this.userService = userService;

    }

    @GetMapping("/protect/profile")
    public ResponseEntity<User> getUserProfileHandler(
           @RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);

        return  new ResponseEntity<User>(user, HttpStatus.ACCEPTED);



    }

    @GetMapping("/protect/search/{query}")
    public  ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query){

        List<User>users = userService.searchUser(query);

        return new ResponseEntity<List<User>>(users,HttpStatus.OK);

    }

    @PutMapping("/protect/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserReq req ,
                                                         @RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);
        userService.updateUser(user.getId(),req);
        ApiResponse apiResponse = new ApiResponse("user update successfully",true);

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);


    }


}
