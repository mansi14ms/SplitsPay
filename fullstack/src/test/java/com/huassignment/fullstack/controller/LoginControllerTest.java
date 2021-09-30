package com.huassignment.fullstack.controller;

import com.huassignment.fullstack.entity.*;
import com.huassignment.fullstack.service.GroupService;
import com.huassignment.fullstack.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @InjectMocks
    LoginController loginController;
    @Mock
    private UserService userService;
    @Mock
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenCreateUserIsSuccessful() {
        //Arrange
        GroupDetails groupDetails = new GroupDetails("abc12345","Party","Birthday treat");
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh","9999999999","mansi@gmail.com","1234");
        Mockito.when(this.userService.createNewUser(userDetails)).thenReturn("User added successfully");

        //Act
        String responseEntity= this.userService.createNewUser(userDetails);

        //Assert
        Assertions.assertEquals("User added successfully",responseEntity);


    }

    @Test
    void whenCreateUserIsNotSuccessful() {
        //Arrange
        GroupDetails groupDetails = new GroupDetails("abc12345","Party","Birthday treat");
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh","9999999999","mansi@gmail.com","1234");
        Mockito.when(this.userService.createNewUser(userDetails)).thenThrow(ResponseStatusException.class);

        //Act


        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.createNewUser(userDetails));


    }




    @Test
    void whenUserSuccessfullylogsIn() {
     //Arrange
     UserLogin userLogin = new UserLogin("mansi","1234");
     Mockito.when(this.userService.verifyAndLoginUser(userLogin)).thenReturn("Successful login!");

     //Act
        String result =this.userService.verifyAndLoginUser(userLogin);

     //Assert
        Assertions.assertEquals("Successful login!",result);

    }

    @Test
    void whenUserlogsDataisIncorrect() {
        //Arrange
        UserLogin userLogin = new UserLogin("mansi","1234");
        Mockito.when(this.userService.verifyAndLoginUser(userLogin)).thenThrow(ResponseStatusException.class);

        //Act


        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.verifyAndLoginUser(userLogin));

    }

    @Test
    void createANewGroup() {
        List<String> members = Arrays.asList("Mansi", "Ashiya");
        GroupWrapper groupWrapper = new GroupWrapper("abcd12345","Party","Birthday",members );
        Mockito.when(this.groupService.createANewGroup(groupWrapper)).thenReturn("Group created");

        //Act
        String result = this.groupService.createANewGroup(groupWrapper);
        result=result.substring(0,13);

        //Assert
        Assertions.assertEquals("Group created",result);

    }

    @Test
    void newTransaction() {
        List<String> members = Arrays.asList("Mansi", "Ashiya");
        GroupWrapper groupWrapper = new GroupWrapper("abcd12345","Party","Birthday",members );
        TransactionWrapper transactionWrapper = new TransactionWrapper("abcd12345","04/04/2021","Swimming","mansi",members,600);
        Mockito.when(this.groupService.addNewTransaction(transactionWrapper)).thenReturn("Transactions added!");

        //Act
        String result = this.groupService.addNewTransaction(transactionWrapper);


        //Assert
        Assertions.assertEquals("Transactions added!",result);

    }
}