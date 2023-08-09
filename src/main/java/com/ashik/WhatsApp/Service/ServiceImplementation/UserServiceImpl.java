package com.ashik.WhatsApp.Service.ServiceImplementation;

import com.ashik.WhatsApp.Config.JwTokenProvider;
import com.ashik.WhatsApp.DTOs.UpdateUserReq;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Repository.UserRepository;
import com.ashik.WhatsApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private JwTokenProvider jwTokenProvider;

    UserServiceImpl(UserRepository userRepository,JwTokenProvider jwTokenProvider){
        this.userRepository = userRepository;
        this.jwTokenProvider = jwTokenProvider;
    }

    @Override
    public User findUserById(Integer id) throws UserException {
         Optional<User> opt = userRepository.findById(id);

         if(opt.isPresent()){
             return opt.get();
         }

        throw  new UserException("user not found with "+ id);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {

        String email = jwTokenProvider.getEmailFromToken(jwt);

        if(email == null){
            throw new BadCredentialsException("Invalid Token");
        }
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw  new UserException("user not found with "+ email);
        }


        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserReq req) throws UserException {
        User user = findUserById(userId);

        if(req.getFull_name() != null){
            user.setFull_name(req.getFull_name());
        }

        if(req.getProfile_picture() != null){
            user.setProfile_picture(req.getProfile_picture());
        }



        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users;
    }
}
