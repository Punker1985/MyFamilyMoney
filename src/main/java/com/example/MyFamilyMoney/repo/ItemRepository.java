package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
