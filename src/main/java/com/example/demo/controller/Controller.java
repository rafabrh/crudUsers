package com.example.demo.controller;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.IdDontExistException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServices;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class Controller {

    private final UserServices userServices;
    private final UserRepository userRepository;

    public Controller(UserServices userServices, UserRepository userRepository) {
        this.userServices = userServices;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        User user = userServices.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UserDto>> listar() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> new UserDto(user.getName())).collect(Collectors.toList());

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public User update(@PathVariable Long id, @RequestBody @Validated UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IdDontExistException("User with ID:" + id + " not found"));
        user.setName(userDto.name());
        return user;
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity<User> delete(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));
        userRepository.delete(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}