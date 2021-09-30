package com.huassignment.fullstack.service;

import com.huassignment.fullstack.entity.GroupUsers;
import com.huassignment.fullstack.entity.UserData;
import com.huassignment.fullstack.entity.UserDetails;
import com.huassignment.fullstack.entity.UserLogin;
import com.huassignment.fullstack.repository.GroupUsersRepository;
import com.huassignment.fullstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private UserVerify userVerify;
    private UserRepository userRepository;
    private GroupUsersRepository groupUsersRepository;
    private EmailService emailService;

    @Autowired
    public UserService(UserVerify userVerify, UserRepository userRepository, GroupUsersRepository groupUsersRepository, EmailService emailService) {
        this.userVerify = userVerify;
        this.userRepository = userRepository;
        this.groupUsersRepository = groupUsersRepository;
        this.emailService = emailService;

    }

    public String createNewUser(UserDetails userDetails) {
        if(!this.userVerify.verifyUserName(userDetails.getUserName()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Username is not acceptable");
        if(!this.userVerify.verifyEmail(userDetails.getEmail()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email is not acceptable");
        this.userRepository.save(userDetails);

       this.emailService.sendEmailToUser(userDetails.getEmail(), "Hello " + userDetails.getFullName(), "Welcome to SplitPay!");

        return "User added successfully";
    }

    public String verifyAndLoginUser(UserLogin userLogin) {
        if(this.userVerify.verifyUserName(userLogin.getUserName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username is not found");

        if(this.userVerify.verifyUserPassword(userLogin.getUserName(), userLogin.getPassword()))
            return "Successful login!";
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
    }

    public List<UserData> getAllUsers() {
        List<UserData> listUserData = new ArrayList<>();
        List<UserDetails> userDetails = StreamSupport
                .stream(this.userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        for (UserDetails userDetail : userDetails) {
            listUserData.add(new UserData(userDetail.getUserName(), userDetail.getFullName(), userDetail.getEmail(), userDetail.getPhone()));
        }

        return listUserData;
    }

    public List<String> getAllFriends(String userName) {
        UserDetails userDetails = this.userRepository.findByUserName(userName);
        List<GroupUsers> groupUsers = this.groupUsersRepository.findAllByUserDetails(userDetails);
        Set<String> friendList = new HashSet<>();
        for (GroupUsers groupUser : groupUsers) {
            List<GroupUsers> groupUsersList = this.groupUsersRepository.findAllByGroupDetails(groupUser.getGroupDetails());
            for (GroupUsers users : groupUsersList) {
                friendList.add(users.getUserDetails().getUserName() + ":" + users.getUserDetails().getPhone());
            }
        }
        return new ArrayList<>(friendList);
    }



}
