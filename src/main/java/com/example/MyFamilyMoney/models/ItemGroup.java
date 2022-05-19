package com.example.MyFamilyMoney.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
public class ItemGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="item_group_id")
    private Long id;
    private TypeOperation typeOperation;
    private String name;
    @OneToMany(mappedBy = "itemGroup", fetch = FetchType.EAGER)
    private Collection<Item> items;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public ItemGroup(TypeOperation typeOperation, String name, User user) {
        this.typeOperation = typeOperation;
        this.name = name;
        this.user = user;
    }

    public ItemGroup() {
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

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return Long.toString(this.id);
    }

    @Override
    public int hashCode() {
        return (id == null ? null : Math.toIntExact(id));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ItemGroup q = (ItemGroup) obj;
        return (this.id == q.id);
    }
    public boolean equals(String q) {
        return (Long.toString(this.id).equals(q));
    }
}
