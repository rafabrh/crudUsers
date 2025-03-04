package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class Controller {

    private final UserServices userServices;
    private final UserRepository userRepository;

    public Controller(UserServices userServices, UserRepository userRepository)
         {this.userServices = userServices;
             this.userRepository = userRepository;
         }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto)
        {
            User user = userServices.createUser(userDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("UP", HttpStatus.OK);


    }
    @GetMapping("/listar")
    public ResponseEntity<List<UserDto>> listar(){
        // Buscar todos os usuários no banco de dados
        List<User> users = userRepository.findAll();

        // Converter a lista de user para DadosListagemUser
        List<UserDto> userDtos = users.stream().map(user -> new UserDto(user.getName())).collect(Collectors.toList());

        // Retornar a lista de DadosListagemUser
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

}
