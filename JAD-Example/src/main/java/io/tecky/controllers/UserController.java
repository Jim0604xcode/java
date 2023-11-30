package io.tecky.controllers;
import io.tecky.dtos.UserContent;

import io.tecky.forms.LoginForm;
import io.tecky.forms.RegisterForm;

import io.tecky.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

//@CrossOrigin(allowedHeaders = "*")
@RestController
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public UserContent getUserByUserId(@PathVariable int userId ){

        var userContents = this.userService.findUserByUserId(userId);
        return userContents;
    }
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public Map login(@Valid @RequestBody LoginForm userForm){
        try{
            if(!userForm.checkUsername(userForm.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username doesn't allow contain ! @ # $ % & * \\ / ?");
            }
            if(!userForm.checkRole(userForm.getRole())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "role must be admin | member | guest");
            }
            var jwt = this.userService.login(userForm.getUsername(),userForm.getPassword());
            return Map.of("success", true,"data",jwt);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    e);
        }
    }
    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public Map register(@Valid @RequestBody RegisterForm userForm){
        try{
            if(!userForm.checkUsername(userForm.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username doesn't allow contain ! @ # $ % & * \\ / ?");
            }
            if(!userForm.checkRole(userForm.getRole())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "role must be admin | member | guest");
            }

            var jwt = this.userService.register(userForm.getPassword(),userForm.getConfirmPassword(),userForm.createUser());
            return Map.of("success", true,"data",jwt);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    e);
        }
    }
}
