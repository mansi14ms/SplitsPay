package com.huassignment.fullstack.entity;

import java.util.List;

public class TransactionWrapper {

    private String groupId;

    private String date;

    private String billFor;

    private String fromUser;

    private List<String> toUser;

    private Integer amount;

    public TransactionWrapper(String groupId, String date, String billFor, String fromUser, List<String> toUser, Integer amount) {
        this.groupId = groupId;
        this.date = date;
        this.billFor = billFor;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public List<String> getToUser() {
        return toUser;
    }

    public void setToUser(List<String> toUser) {
        this.toUser = toUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
