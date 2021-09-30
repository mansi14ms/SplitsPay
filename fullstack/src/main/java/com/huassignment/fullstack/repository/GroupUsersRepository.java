package com.huassignment.fullstack.repository;

import com.huassignment.fullstack.entity.GroupDetails;
import com.huassignment.fullstack.entity.GroupUsers;
import com.huassignment.fullstack.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupUsersRepository extends CrudRepository<GroupUsers,Integer> {

    List<GroupUsers> findAllByUserDetails(UserDetails userDetails);
    List<GroupUsers> findAllByGroupDetails(GroupDetails groupDetails);
}
