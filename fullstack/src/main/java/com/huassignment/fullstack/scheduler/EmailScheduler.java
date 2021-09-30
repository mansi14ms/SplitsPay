package com.huassignment.fullstack.scheduler;

import com.huassignment.fullstack.entity.TransactionDetails;
import com.huassignment.fullstack.entity.UserDetails;
import com.huassignment.fullstack.repository.GroupUsersRepository;
import com.huassignment.fullstack.repository.TransactionRepository;
import com.huassignment.fullstack.repository.UserRepository;
import com.huassignment.fullstack.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailScheduler {

    private UserRepository userRepository;
    private EmailService emailService;
    private TransactionRepository transactionRepository;

    @Autowired
    public EmailScheduler(UserRepository userRepository, EmailService emailService, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(cron="0 0 0 */5 * *")
    public void sendSummaryReportToUsers() {
        List<UserDetails> userDetailsList  = (List<UserDetails>) this.userRepository.findAll();
        List<String> userList = new ArrayList<>();
        for(UserDetails userDetails : userDetailsList) {
            List<TransactionDetails> transactionDetailsList = this.transactionRepository.findAllByFromUserDetails(userDetails);
            int owedAmount = 0;
            for(TransactionDetails transactionDetails : transactionDetailsList ) {
                owedAmount += transactionDetails.getAmount();
            }
            transactionDetailsList = this.transactionRepository.findAllByToUserDetails(userDetails);
            int owesAmount = 0;
            for(TransactionDetails transactionDetails : transactionDetailsList ) {
                owesAmount += transactionDetails.getAmount();
            }
            int total = owedAmount - owesAmount;
            String body = userDetails.getFullName() + ", you are owed : " + owedAmount + ", you owe : " + owesAmount + " and your balance is : " + total;
            this.emailService.sendEmailToUser(userDetails.getEmail(), "User Summary : " + userDetails.getFullName(), body);
            userList.add(userDetails.getUserName() + " " + owedAmount + " " + owesAmount);

        }

    }
}
