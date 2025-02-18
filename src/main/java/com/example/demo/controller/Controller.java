package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class Controller {

    private final UserServices userServices;

    public Controller(UserServices userServices)
         {this.userServices = userServices;}


    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto)
        {
            User user = userServices.createUser(userDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }



}
