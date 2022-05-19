package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_group_id")
    private Long id;
    private String groupName, password;


    public UserGroup() {
    }

    public UserGroup(String groupName, String password) {
        this.groupName = groupName;
        this.password = password;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
