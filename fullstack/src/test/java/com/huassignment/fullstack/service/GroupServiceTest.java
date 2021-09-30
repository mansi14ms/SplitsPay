package com.huassignment.fullstack.service;

import com.huassignment.fullstack.entity.*;
import com.huassignment.fullstack.repository.GroupRepository;
import com.huassignment.fullstack.repository.GroupUsersRepository;
import com.huassignment.fullstack.repository.TransactionRepository;
import com.huassignment.fullstack.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class GroupServiceTest {


    @InjectMocks
    private GroupService groupService;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupUsersRepository groupUsersRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        groupService =new GroupService(groupRepository,userRepository,groupUsersRepository,transactionRepository, emailService);
    }

    @Test
    void whenGroupCreationIsSuccessFul() {
        //Arrange
        GroupDetails groupDetails = new GroupDetails("dog12345","Party","Birthday treat");
        List<String> members = Arrays.asList("Mansi", "Ashiya");
        GroupWrapper groupWrapper = new GroupWrapper("abcd12345","Party","Birthday",members );

        //Act
        String result = groupService.createANewGroup(groupWrapper);
        result = result.substring(0,13);

        //Assert
        Assertions.assertEquals("Group created",result);
    }

    @Test
    void whenNewExpenseIsAdded() {
        //Arrange
        List<String> members = Arrays.asList("mansi1", "rK");
        TransactionWrapper transactionWrapper = new TransactionWrapper("abcd12345","04/04/2021","Swimming","mansi",members,600);
        GroupDetails groupDetails = new GroupDetails("abc12345","Party","Birthday treat");
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh","9999999999","mansi@gmail.com","1234");
        UserDetails toUserDetails = new UserDetails("mansi","Mansi Singh","9999999999","mansi@gmail.com","1234");
        Mockito.when(this.userRepository.findByUserName(transactionWrapper.getFromUser())).thenReturn(userDetails);
        Mockito.when(this.userRepository.findByUserName(transactionWrapper.getToUser().get(0))).thenReturn(toUserDetails);
        Mockito.when(this.groupRepository.findById(transactionWrapper.getGroupId())).thenReturn(java.util.Optional.of(groupDetails));

        //Act
        String result = groupService.addNewTransaction(transactionWrapper);

        //Assert
        Assertions.assertEquals("Transactions added!",result);

    }

    @Test
    void whenCalculatingTotalExpense() {

        //Arrange

        //Act
        System.out.println("result");
        String result = groupService.totalUserBalance("mansi1");
        result=result.substring(0,5);


        //Assert
        Assertions.assertEquals("Total",result);

    }

    @Test
    void whenAllGroupsByUserNameIsReturned() {

        List<String> members = Arrays.asList("mansi1", "rK");
        GroupDetails groupDetails = new GroupDetails("abc12345","Party","Birthday treat");
        UserDetails userDetails = new UserDetails("mansi","Mansi Singh","9999999999","mansi@gmail.com","1234");
        GroupUsers groupUsers = new GroupUsers(18,groupDetails,userDetails);
        List<GroupUsers> group = new ArrayList<>();
        //group.add(groupUsers);
        Mockito.when(this.userRepository.findByUserName("mansi")).thenReturn(userDetails);
        Mockito.when(this.groupService.getAllGroupsByUserName("mansi")).thenReturn(group);

        //Act
        List<GroupUsers> result= groupService.getAllGroupsByUserName("mansi1");
        Mockito.when(this.groupService.getAllGroupsByUserName("mansi")).thenReturn(group);

        //Assert
        Assertions.assertEquals(group,result);
    }

}