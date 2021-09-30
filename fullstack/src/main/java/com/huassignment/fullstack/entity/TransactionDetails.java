package com.huassignment.fullstack.entity;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transactions")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "groupId")
    private GroupDetails groupDetails;

    @Column
    private String date;

    @Column
    private String billFor;

    @OneToOne
    @JoinColumn(name = "fromUserName")
    private UserDetails fromUserDetails;

    @OneToOne
    @JoinColumn(name = "toUserName")
    private UserDetails toUserDetails;

    @Column
    private Integer amount;

    public TransactionDetails() {}

    public TransactionDetails(Integer id, GroupDetails groupDetails, String date, String billFor, UserDetails fromUserDetails, UserDetails toUserDetails, Integer amount) {
        this.id = id;
        this.groupDetails = groupDetails;
        this.date = date;
        this.billFor = billFor;
        this.fromUserDetails = fromUserDetails;
        this.toUserDetails = toUserDetails;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GroupDetails getGroupId() {
        return groupDetails;
    }

    public void setGroupId(GroupDetails groupDetails) {
        this.groupDetails = groupDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillFor() {
        return billFor;
    }

    public void setBillFor(String billFor) {
        this.billFor = billFor;
    }

    public UserDetails getFromUserDetails() {
        return fromUserDetails;
    }

    public void setFromUserDetails(UserDetails fromUserDetails) {
        this.fromUserDetails = fromUserDetails;
    }

    public UserDetails getToUserDetails() {
        return toUserDetails;
    }

    public void setToUserDetails(UserDetails toUserDetails) {
        this.toUserDetails = toUserDetails;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
