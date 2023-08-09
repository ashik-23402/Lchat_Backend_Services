package com.ashik.WhatsApp.Controllers;

import com.ashik.WhatsApp.Config.JwTokenProvider;
import com.ashik.WhatsApp.DTOs.LoginRequest;
import com.ashik.WhatsApp.Entities.User;
import com.ashik.WhatsApp.Exceptions.UserException;
import com.ashik.WhatsApp.Repository.UserRepository;
import com.ashik.WhatsApp.Response.AuthResponse;
import com.ashik.WhatsApp.Service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private JwTokenProvider jwTokenProvider;

    private CustomUserService customUserService;



    public  AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           JwTokenProvider jwTokenProvider,
                           CustomUserService customUserService){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwTokenProvider = jwTokenProvider;
        this.customUserService = customUserService;



    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> creatUserHandler(@RequestBody User user) throws UserException {

        String email = user.getEmail();
        String full_name = user.getFull_name();
        String password = user.getPassword();

        User user1 = userRepository.findByEmail(email);

        if(user1 != null){
            throw new UserException("alrady this email is used by another account");
        }

        User createUser = new User();
        createUser.setEmail(email);
        createUser.setFull_name(full_name);
        createUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwTokenProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);


    }

    public Authentication authenticate(String email, String password){

        UserDetails userDetails = customUserService.loadUserByUsername(email);

        if(userDetails == null){
            throw new BadCredentialsException("invalid username");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());


    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody  LoginRequest request){

        String email = request.getEmail();
        String password = request.getPassword();

         Authentication authentication =authenticate(email,password);
         SecurityContextHolder.getContext().setAuthentication(authentication);

         String token = jwTokenProvider.generateToken(authentication);
         AuthResponse res = new AuthResponse(token,true);

         return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);


    }


}
