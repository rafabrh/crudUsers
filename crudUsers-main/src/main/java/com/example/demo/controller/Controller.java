package com.example.demo.controller;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServices;
import jakarta.transaction.Transactional;
//import lombok.var;
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

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("UP", HttpStatus.OK);


    }

    @GetMapping("/listar")
    public ResponseEntity<List<UserDto>> listar() {
        // buscar todos os usuários no banco de dados
        List<User> users = userRepository.findAll();

        // converter a lista de user para DadosListagemUser
        List<UserDto> userDtos = users.stream().map(user -> new UserDto(user.getName())).collect(Collectors.toList());

        // retornar a lista de DadosListagemUser
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<User> atualizar(@PathVariable Long id, @RequestBody @Validated UserDto userDto) {
        //encontrar o usuário pelo ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + "not found"));

        // atualizar os dados do usuário
        user.setName(userDto.getName());

        // salva usuário atualizado
        User updatedUser = userRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}