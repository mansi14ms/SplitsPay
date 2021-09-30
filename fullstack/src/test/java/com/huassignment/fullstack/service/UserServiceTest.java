package com.huassignment.fullstack.service;

import com.huassignment.fullstack.entity.UserDetails;
import com.huassignment.fullstack.entity.UserLogin;
import com.huassignment.fullstack.repository.GroupUsersRepository;
import com.huassignment.fullstack.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserVerify userVerify;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupUsersRepository groupUsersRepository;
    @Mock
    private EmailService emailService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService =new UserService(userVerify, userRepository, groupUsersRepository,emailService);
    }

    @Test
    void whenCreateNewUserIsSuccessful() {

        //Arrange
        UserDetails userDetails = new UserDetails("dustbin","Dustbin Singh","9999999999","mansi@gmail.com","1234");
        Mockito.when(userVerify.verifyUserName(userDetails.getUserName())).thenReturn(true);
        Mockito.when(userVerify.verifyEmail(userDetails.getEmail())).thenReturn(true);
        Mockito.when(userRepository.save(userDetails)).thenReturn(null);

        //Act
        String result = userService.createNewUser(userDetails);

        //Assert
        Assertions.assertEquals("User added successfully",result);
    }

    @Test
    void whenCreateNewUserIsUnSuccessfulDueToEmail() {

        //Arrange
        UserDetails userDetails = new UserDetails("dustbin","Dustbin Singh","9999999999","mansi@gmail.com","1234");
        Mockito.when(userVerify.verifyUserName(userDetails.getUserName())).thenReturn(true);
        Mockito.when(userVerify.verifyEmail(userDetails.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(userDetails)).thenReturn(null);

        //Act

        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.createNewUser(userDetails));
    }

    @Test
    void whenCreateNewUserIsUnSuccessfulDueToUserName() {

        //Arrange
        UserDetails userDetails = new UserDetails("dustbin","Dustbin Singh","9999999999","mansi@gmail.com","1234");
        Mockito.when(userVerify.verifyUserName(userDetails.getUserName())).thenReturn(false);
        Mockito.when(userVerify.verifyEmail(userDetails.getEmail())).thenReturn(true);
        Mockito.when(userRepository.save(userDetails)).thenReturn(null);

        //Act

        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.createNewUser(userDetails));
    }

    @Test
    void userLoginIsSuccessful() {

        //Arrange
        UserLogin userLogin = new UserLogin("mat","bat");
        Mockito.when(userVerify.verifyUserName(userLogin.getUserName())).thenReturn(false);
        Mockito.when(userVerify.verifyUserPassword(userLogin.getUserName(), userLogin.getPassword())).thenReturn(true);

        //Act
        String result = userService.verifyAndLoginUser(userLogin);

        //Assert
        Assertions.assertEquals("Successful login!",result);
    }

    @Test
    void whenUserNameIsInvalidDuringLogin() {

        //Arrange
        UserLogin userLogin = new UserLogin("mat","bat");
        Mockito.when(userVerify.verifyUserName(userLogin.getUserName())).thenReturn(true);
        Mockito.when(userVerify.verifyUserPassword(userLogin.getUserName(), userLogin.getPassword())).thenReturn(true);

        //Act

        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.verifyAndLoginUser(userLogin));
    }

    @Test
    void whenPasswordIsInvalidDuringLogin() {

        //Arrange
        UserLogin userLogin = new UserLogin("mat","bat");
        Mockito.when(userVerify.verifyUserName(userLogin.getUserName())).thenReturn(true);
        Mockito.when(userVerify.verifyUserPassword(userLogin.getUserName(), userLogin.getPassword())).thenReturn(false);

        //Act

        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.verifyAndLoginUser(userLogin));
    }



}