package com.ashik.WhatsApp.Service;

import com.ashik.WhatsApp.DTOs.UpdateUserReq;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.UserException;

import java.util.List;

public interface UserService {

    public User findUserById(Integer id) throws UserException;

    public  User findUserProfile(String jwt) throws UserException;

    public  User updateUser(Integer userId, UpdateUserReq req) throws UserException;

    public List<User> searchUser(String query);


}
