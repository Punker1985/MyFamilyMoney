package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transfer extends Transactions{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @ManyToOne
    private Account accountSpending;
    @ManyToOne
    private Account accountReceipt;
//    private Long amount;
//    private LocalDateTime date;
//    private String description;

    public Transfer() {
    }

    public Transfer(Account accountSpending, Account accountReceipt, Long amount, LocalDateTime date, String description) {
        super(amount, description, date);
        this.accountSpending = accountSpending;
        this.accountReceipt = accountReceipt;
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
}
