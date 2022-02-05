package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Account accountSpending;
    @ManyToOne
    private Account accountReceipt;
    private Long amount;
    private LocalDateTime date;
    private String description;

    public Transfer() {
    }

    public Transfer(Account accountSpending, Account accountReceipt, Long amount, LocalDateTime date, String description) {
        this.accountSpending = accountSpending;
        this.accountReceipt = accountReceipt;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccountSpending() {
        return accountSpending;
    }

    public void setAccountSpending(Account accountSpending) {
        this.accountSpending = accountSpending;
    }

    public Account getAccountReceipt() {
        return accountReceipt;
    }

    public void setAccountReceipt(Account accountReceipt) {
        this.accountReceipt = accountReceipt;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
