package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class Operations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Counteragent counteragent;
    @ManyToOne
    private Item item;
    private LocalDateTime date;
    private Long amount;

    public Operations(String description, Account account, Counteragent counteragent, LocalDateTime date, Item item, Long amount) {
        this.description = description;
        this.account = account;
        this.counteragent = counteragent;
        this.item = item;
        this.date = date;
        this.amount = amount;
    }

    public Operations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Counteragent getCounteragent() {
        return counteragent;
    }

    public void setCounteragent(Counteragent counteragent) {
        this.counteragent = counteragent;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
