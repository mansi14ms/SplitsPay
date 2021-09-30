package com.huassignment.fullstack.service;

import com.huassignment.fullstack.entity.UserDetails;
import com.huassignment.fullstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVerify {

    private UserRepository userRepository;

    @Autowired
    public UserVerify(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifyUserName(String username) {
        return this.userRepository.findByUserName(username) == null;
    }

    public boolean verifyEmail(String email) {
        return this.userRepository.findByEmail(email) == null;
    }

    public boolean verifyUserPassword(String userName, String password) {
        UserDetails userDetails = this.userRepository.findByUserName(userName);
        return userDetails.getPassword().equals(password);
    }

}
