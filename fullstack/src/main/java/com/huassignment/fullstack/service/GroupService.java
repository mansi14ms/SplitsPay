package com.huassignment.fullstack.service;

import com.huassignment.fullstack.entity.*;
import com.huassignment.fullstack.repository.GroupRepository;
import com.huassignment.fullstack.repository.GroupUsersRepository;
import com.huassignment.fullstack.repository.TransactionRepository;
import com.huassignment.fullstack.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {


    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private GroupUsersRepository groupUsersRepository;
    private TransactionRepository transactionRepository;
    private EmailService emailService;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, GroupUsersRepository groupUsersRepository, TransactionRepository transactionRepository, EmailService emailService) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupUsersRepository = groupUsersRepository;
        this.transactionRepository = transactionRepository;
        this.emailService = emailService;
    }

    public String createANewGroup(GroupWrapper groupWrapper) {
        GroupDetails groupDetails = new GroupDetails(groupWrapper.getGroupId(), groupWrapper.getGroupName(), groupWrapper.getDescription());
        this.groupRepository.save(groupDetails);
        for(int i=0; i<groupWrapper.getMembers().size(); i++) {
            UserDetails userDetails = this.userRepository.findByUserName(groupWrapper.getMembers().get(i));
            GroupUsers groupUsers = new GroupUsers(null, groupDetails, userDetails);
            this.groupUsersRepository.save(groupUsers);
        }
        return "Group created with group id : " + groupWrapper.getGroupId();
    }

    public List<GroupUsers> getAllGroupsByUserName(String userName) {
        UserDetails userDetails = this.userRepository.findByUserName(userName);
        return this.groupUsersRepository.findAllByUserDetails(userDetails);
    }

    public List<GroupUsers> getAllGroupMembers(String groupId) {
        GroupDetails groupDetails = this.groupRepository.findById(groupId).get();
        return this.groupUsersRepository.findAllByGroupDetails(groupDetails);
    }

    public String addNewTransaction(TransactionWrapper transactionWrapper) {

        for( int i=0; i<transactionWrapper.getToUser().size(); i++) {
            TransactionDetails transactionDetails = new TransactionDetails();
            UserDetails fromUserDetails = this.userRepository.findByUserName(transactionWrapper.getFromUser());
            transactionDetails.setFromUserDetails(fromUserDetails);
            UserDetails toUserDetails = this.userRepository.findByUserName(transactionWrapper.getToUser().get(i));
            transactionDetails.setFromUserDetails(fromUserDetails);
            transactionDetails.setToUserDetails(toUserDetails);
            transactionDetails.setAmount(transactionWrapper.getAmount());
            transactionDetails.setDate(transactionWrapper.getDate());
            transactionDetails.setBillFor(transactionWrapper.getBillFor());
            GroupDetails groupDetails = this.groupRepository.findById(transactionWrapper.getGroupId()).get();
            transactionDetails.setGroupId(groupDetails);
            this.transactionRepository.save(transactionDetails);

            try {
                this.emailService.sendEmailToUser(toUserDetails.getEmail(), "New transaction is added", "A transaction is added by " + fromUserDetails.getFullName() + " you owe them " + transactionWrapper.getAmount() + " for " + transactionWrapper.getBillFor());
            } catch (NullPointerException ignored) {}
        }

        return "Transactions added!";
    }

    public String totalUserBalance(String username) {
        UserDetails userDetails = this.userRepository.findByUserName(username);
        List<TransactionDetails> transactionDetails = this.transactionRepository.findAllByFromUserDetails(userDetails);
        int totalBalance = 0;
        for (TransactionDetails transactionDetail : transactionDetails) {
            totalBalance += transactionDetail.getAmount();
        }
        transactionDetails = this.transactionRepository.findAllByToUserDetails(userDetails);
        for (TransactionDetails transactionDetail : transactionDetails) {
            totalBalance -= transactionDetail.getAmount();
        }
        return "Total balance is : " + totalBalance;

    }

    public List<TransactionDetails> getAllTransactionsByGroup(String groupId) {
        GroupDetails groupDetails = this.groupRepository.findById(groupId).get();
        return this.transactionRepository.findAllByGroupDetails(groupDetails);
    }

}
