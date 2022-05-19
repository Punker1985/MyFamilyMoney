package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class Operations extends Transactions {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    //   private String description;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Counteragent counteragent;
    @ManyToOne
    private Item item;
    //   private LocalDateTime date;
//    private Long amount;

    public Operations(String description, Account account, Counteragent counteragent, LocalDateTime date, Item item, Long amount) {
        super(amount, description, date);
        this.account = account;
        this.counteragent = counteragent;
        this.item = item;
    }

    public Operations(String description, Account account, LocalDateTime date, Item item, Long amount) {
        super(amount, description, date);
        this.account = account;
        this.item = item;
    }

    public Operations() {
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
}
