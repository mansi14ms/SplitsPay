package com.huassignment.fullstack.service;

import com.huassignment.fullstack.entity.GroupDetails;
import com.huassignment.fullstack.entity.GroupWrapper;
import com.huassignment.fullstack.entity.UserDetails;
import com.huassignment.fullstack.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserVerifyTest {

    @InjectMocks
    private UserVerify userVerify;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userVerify =new UserVerify(userRepository);
    }

    @Test
    void userNameDoesNotExist() {

        //Arrange
        GroupDetails groupDetails = new GroupDetails("dog12345","Party","Birthday treat");
        List<String> members = Arrays.asList("Mansi", "Ashiya");
        GroupWrapper groupWrapper = new GroupWrapper("abcd12345","Party","Birthday",members );
        Mockito.when(this.userRepository.findByUserName("mansi")).thenReturn(null);

        //Act
        boolean result = userVerify.verifyUserName("mansi");

        //Assert
        Assertions.assertEquals(true,result);
    }

    @Test
    void userNameAlreadyExists() {

        //Arrange
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh", "9191919191", "mansi@gmail.com","1234");
        Mockito.when(this.userRepository.findByUserName("mansi")).thenReturn(userDetails);

        //Act
        boolean result = userVerify.verifyUserName("mansi");

        //Assert
        Assertions.assertEquals(false,result);
    }

    @Test
    void userEmailAlreadyExists() {

        //Arrange
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh", "9191919191", "mansi@gmail.com","1234");
        Mockito.when(this.userRepository.findByEmail("mansi@gmail.com")).thenReturn(userDetails);

        //Act
        boolean result = userVerify.verifyEmail("mansi@gmail.com");

        //Assert
        Assertions.assertEquals(false,result);
    }

    @Test
    void userEmailDoesNotExist() {

        //Arrange
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh", "9191919191", "mansi@gmail.com","1234");
        Mockito.when(this.userRepository.findByEmail("mansi@gmail.com")).thenReturn(null);

        //Act
        boolean result = userVerify.verifyEmail("mansi@gmail.com");

        //Assert
        Assertions.assertEquals(true,result);
    }

    @Test
    void passwordMatches() {

        //Arrange
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh", "9191919191", "mansi@gmail.com","1234");
        Mockito.when(this.userRepository.findByUserName("mansi@gmail.com")).thenReturn(userDetails);

        //Act
        boolean result = userVerify.verifyUserPassword("mansi@gmail.com","1234");

        //Assert
        Assertions.assertEquals(true,result);
    }

    @Test
    void passwordDoesNotMatches() {

        //Arrange
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh", "9191919191", "mansi@gmail.com","1234");
        Mockito.when(this.userRepository.findByUserName("mansi@gmail.com")).thenReturn(userDetails);

        //Act
        boolean result = userVerify.verifyUserPassword("mansi@gmail.com","1234567");

        //Assert
        Assertions.assertEquals(false,result);
    }




}