package com.huassignment.fullstack.entity;

import javax.persistence.*;

@Entity
@Table(name = "groupers")
public class GroupUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "groupId")
    private GroupDetails groupDetails;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserDetails userDetails;

    public GroupUsers() {}

    public GroupUsers(Integer id, GroupDetails groupDetails, UserDetails userDetails) {
        this.id = id;
        this.groupDetails = groupDetails;
        this.userDetails = userDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GroupDetails getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(GroupDetails groupDetails) {
        this.groupDetails = groupDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
