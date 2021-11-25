package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Operations {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String description;
  private TypeOperation type;
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
  @ManyToOne
  @JoinColumn(name = "counteragent_id")
  private Counteragent counteragent;
  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;
  private Instant date;
  private float amount;

  public Operations() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TypeOperation getType() {
    return type;
  }

  public void setType(TypeOperation type) {
    this.type = type;
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

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }
}
