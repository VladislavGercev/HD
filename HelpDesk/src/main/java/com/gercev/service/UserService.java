package com.gercev.service;


import com.gercev.domain.User;

import com.gercev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<List<User>> getManagers(){
        return userRepository.getManagers();
    }
    public Optional<List<User>> getEngineers(){
        return userRepository.getEngineers();
    }




}
