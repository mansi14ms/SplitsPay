package com.huassignment.fullstack.entity;

import java.util.List;

public class GroupWrapper {

    private String groupId;
    private String groupName;
    private String description;
    private List<String> members;

    public GroupWrapper(String groupId, String groupName, String description, List<String> members) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.members = members;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
