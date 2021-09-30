package com.huassignment.fullstack.repository;

import com.huassignment.fullstack.entity.GroupDetails;
import com.huassignment.fullstack.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<GroupDetails,String> {
}
