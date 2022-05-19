package com.example.MyFamilyMoney.models;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TypeOperation typeOperation;
    private String name;
    @ManyToOne
    @JoinColumn(name="item_group_id", nullable = false)
    private ItemGroup itemGroup;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Item() {
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

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item(TypeOperation typeOperation, String name, ItemGroup itemGroup) {
        this.typeOperation = typeOperation;
        this.name = name;
        this.itemGroup = itemGroup;
    }
}
