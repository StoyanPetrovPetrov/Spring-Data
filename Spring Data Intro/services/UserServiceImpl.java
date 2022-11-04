package com.example.springinitdemo.services;

import com.example.springinitdemo.models.Account;
import com.example.springinitdemo.models.User;
import com.example.springinitdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public void register(String username, int age) {
        if (username.isBlank() || age < 18){
            throw new RuntimeException("Validation Failed");
        }
        Optional<User> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isPresent()){
            throw new RuntimeException("Username already in use");
        }

    }
    Account account = new Account();






    }
