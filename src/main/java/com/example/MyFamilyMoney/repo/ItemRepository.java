package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.*;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findAllByTypeOperation(TypeOperation typeOperation);
    Iterable<Item> findByUserOrUser(User user, User user1);
    Iterable<Item> findByUser(User user);
}

