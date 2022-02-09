package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.Account;
import com.example.MyFamilyMoney.models.Item;
import com.example.MyFamilyMoney.models.Operations;
import com.example.MyFamilyMoney.models.TypeOperation;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findAllByTypeOperation(TypeOperation typeOperation);
}

