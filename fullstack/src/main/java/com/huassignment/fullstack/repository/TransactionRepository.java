package com.huassignment.fullstack.repository;

import com.huassignment.fullstack.entity.GroupDetails;
import com.huassignment.fullstack.entity.GroupUsers;
import com.huassignment.fullstack.entity.TransactionDetails;
import com.huassignment.fullstack.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDetails,Integer> {
    List<TransactionDetails> findAllByFromUserDetails(UserDetails userDetails);
    List<TransactionDetails> findAllByToUserDetails(UserDetails userDetails);
    List<TransactionDetails> findAllByGroupDetails(GroupDetails groupDetails);
}
