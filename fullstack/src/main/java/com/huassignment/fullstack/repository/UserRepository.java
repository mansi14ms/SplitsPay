package com.huassignment.fullstack.repository;

import com.huassignment.fullstack.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDetails,String> {

    UserDetails findByUserName(String username);
    UserDetails findByEmail(String email);

}
