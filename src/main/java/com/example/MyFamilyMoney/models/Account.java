package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private AccountType type;
    @ManyToOne
    private User user;
    @NotBlank(message = "не верно имя")
    private String name;
    @NotNull(message = "начальный баланс не верен")
    @Min(0)
    private Long startBalance;
    private Long endBalance;

    public Account() {
    }

    public Account(String name, AccountType type, User user, Long startBalance, Long endBalance) {
        this.name = name;
        this.type = type;
        this.user = user;
        this.startBalance = startBalance;
        this.endBalance = endBalance;
    }

    public String toString(){
        return Long.toString(this.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(Long startBalance) {
        this.startBalance = startBalance;
    }

    public Long getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Long endBalance) {
        this.endBalance = endBalance;
    }
}
