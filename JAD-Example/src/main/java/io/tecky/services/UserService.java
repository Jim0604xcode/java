package io.tecky.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.tecky.dtos.UserContent;
import io.tecky.filter.JsonWebToken;
import io.tecky.models.User;
import io.tecky.repositories.UserRepository;


import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
//@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    private final JsonWebToken jsonWebToken;


    public UserService(JsonWebToken jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }
    public UserContent findUserByUserId(int userId){
        var userContents = userRepository.findUserContentsForUserWithoutNativeQuery(userId);
        return userContents;
    }
    public UserContent findUserByUserId2(int userId){
        var userContents = userRepository.findUserContentsForUserWithNativeQuery(userId);
        return userContents.get(0);
    }

    public String login(String username,String password){
        var users = userRepository.findUsersByUsername(username);
        if(users.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doesn't exist user for the username");
        }
        var user = users.get(0);
        var result = BCrypt.verifyer().verify(password.getBytes(),
                user.getPassword().getBytes());
        if(!result.verified){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect username/password");
        }
        return this.jsonWebToken.encodeJWT(user.getId());
    }

    public String register(String password, String confirmPassword, User newUser){
        if(!password.equals(confirmPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password not match");
        }
        newUser.setPassword(BCrypt.withDefaults().hashToString(10,password.toCharArray()));
        User dbUser = userRepository.saveAndFlush(newUser);
        userRepository.refresh(dbUser);

        System.out.println(dbUser.toString());
        return this.jsonWebToken.encodeJWT(dbUser.getId());
    }
}
