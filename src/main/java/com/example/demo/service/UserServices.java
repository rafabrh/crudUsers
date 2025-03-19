package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    private final UserRepository userRepository;
    public UserRepository UserRepository;

    public UserServices(UserRepository UserRepository, UserRepository userRepository) {
        this.UserRepository = UserRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public User createUser(UserDto userDto){
        User user = new User(userDto.name());
        return userRepository.save(user);
    }

}
