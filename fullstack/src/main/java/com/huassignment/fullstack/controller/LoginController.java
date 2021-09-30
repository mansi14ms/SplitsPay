package com.huassignment.fullstack.controller;

import com.huassignment.fullstack.entity.*;
import com.huassignment.fullstack.scheduler.EmailScheduler;
import com.huassignment.fullstack.service.GroupService;
import com.huassignment.fullstack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
public class LoginController {

    private UserService userService;
    private GroupService groupService;
    private EmailScheduler emailScheduler;

    @Autowired
    public LoginController(UserService userService, GroupService groupService, EmailScheduler emailScheduler) {
        this.userService = userService;
        this.groupService = groupService;
        this.emailScheduler = emailScheduler;
    }

    @RequestMapping("/")
    public String helloWorld()
    {
        return "Welcome to SplitPay";
    }

    @PostMapping("/signup")
    public String createUser(@RequestBody UserDetails userDetails) {
        try {
            return this.userService.createNewUser(userDetails);
        }
        catch(ResponseStatusException rse) {
            return rse.getMessage();
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserLogin userLogin) {
        try {
            return this.userService.verifyAndLoginUser(userLogin);
        }
        catch(ResponseStatusException rse) {
            return rse.getMessage();
        }

    }

    @GetMapping("/users")
    public List<UserData> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping("/group")
    public String createANewGroup(@RequestBody GroupWrapper groupWrapper) {
        return this.groupService.createANewGroup(groupWrapper);
    }

    @GetMapping("/group/{userName}")
    public List<GroupUsers> getAllUsersByUserName(@PathVariable("userName") String userName) {
        return this.groupService.getAllGroupsByUserName(userName);
    }

    @GetMapping("/group/users/{groupId}")
    public List<GroupUsers> getAllGroupMembers(@PathVariable("groupId") String groupId) {
        return this.groupService.getAllGroupMembers(groupId);
    }

    @PostMapping("/transactions")
    public String executeTransaction(@RequestBody TransactionWrapper transactionWrapper) {
        return this.groupService.addNewTransaction(transactionWrapper);
    }

    @GetMapping("/amount/total/{userName}")
    public String getUserTotalBalance(@PathVariable("userName") String userName) {
        return this.groupService.totalUserBalance(userName);
    }

    @GetMapping("/transactions/{groupId}")
    public List<TransactionDetails> getAllTransactionDetailsByGroup(@PathVariable("groupId") String groupId) {
        return this.groupService.getAllTransactionsByGroup(groupId);
    }

    @GetMapping("/friends/{userName}")
    public List<String> getAllFriends(@PathVariable("userName") String userName) {
        return this.userService.getAllFriends(userName);
    }

    @GetMapping("/balances")
    public void getAllBalances() {
        this.emailScheduler.sendSummaryReportToUsers();
    }



}
